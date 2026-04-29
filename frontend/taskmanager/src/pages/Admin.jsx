import { useState, useEffect, useCallback } from "react";
import api from "../api";

// ─── Constants ────────────────────────────────────────────────────────────────
const DEGREES    = ["BACHELOR", "MASTER", "PHD", "DOCTOR"];
const DIRECTIONS = ["PROGRAMMING", "BIG_DATA", "GENERAL"];
const NAV        = ["profiles", "groups", "subjects", "enrollments","lessons","assignments","classrooms"];
const NAV_META   = {
  profiles:    { label: "Profiles",    color: "#1D9E75" },
  groups:      { label: "Groups",      color: "#378ADD" },
  subjects:    { label: "Subjects",    color: "#7F77DD" },
  enrollments: { label: "Enrollments", color: "#D85A30" },
  lessons: { label: "Lessons", color: "#C2410C" },
  assignments: { label: "Assignments", color: "#B45309" },
  classrooms: { label: "Schedule", color: "#0369A1" }
};

const DIR_STYLE = {
  PROGRAMMING: { color: "#185FA5", bg: "#E6F1FB" },
  BIG_DATA:    { color: "#1D9E75", bg: "#E1F5EE" },
  GENERAL:     { color: "#534AB7", bg: "#EEEDFE" },
};

// ─── Base Styles ──────────────────────────────────────────────────────────────
const inp = {
  width: "100%", padding: "9px 12px", fontSize: 13, borderRadius: 8,
  border: "0.5px solid #d0d0d0", background: "#fff", color: "#111",
  outline: "none", boxSizing: "border-box", fontFamily: "inherit",
};
const btnPrimary = {
  padding: "9px 22px", fontSize: 13, fontWeight: 600, borderRadius: 8,
  border: "none", background: "#111", color: "#fff", cursor: "pointer",
};
const btnGhost = {
  padding: "6px 12px", fontSize: 12, borderRadius: 6,
  border: "0.5px solid #d0d0d0", background: "transparent", color: "#444", cursor: "pointer",
};
const btnDanger = {
  padding: "6px 12px", fontSize: 12, borderRadius: 6,
  border: "0.5px solid #fca5a5", background: "transparent", color: "#b91c1c", cursor: "pointer",
};

// ─── Shared UI Components ─────────────────────────────────────────────────────
function Badge({ children, color = "#555", bg = "#f0f0f0" }) {
  return (
    <span style={{ display: "inline-block", padding: "2px 9px", borderRadius: 99, fontSize: 11, fontWeight: 600, color, background: bg }}>
      {children}
    </span>
  );
}

function Field({ label, children }) {
  return (
    <div style={{ marginBottom: 14 }}>
      <label style={{ display: "block", fontSize: 11, fontWeight: 600, color: "#888", marginBottom: 5, textTransform: "uppercase", letterSpacing: "0.06em" }}>
        {label}
      </label>
      {children}
    </div>
  );
}

function StatusBar({ msg }) {
  if (!msg) return null;
  const isErr = msg.startsWith("Error");
  return (
    <div style={{
      padding: "10px 14px", borderRadius: 8, marginBottom: 14, fontSize: 13,
      background: isErr ? "#fef2f2" : "#f0fdf4",
      color: isErr ? "#b91c1c" : "#065f46",
      border: `0.5px solid ${isErr ? "#fca5a5" : "#6ee7b7"}`,
    }}>
      {msg}
    </div>
  );
}

function Modal({ title, onClose, onSubmit, submitLabel = "Save", loading, children }) {
  return (
    <div style={{ position: "fixed", inset: 0, background: "rgba(0,0,0,0.38)", display: "flex", alignItems: "center", justifyContent: "center", zIndex: 1000 }}>
      <div style={{ background: "#fff", borderRadius: 14, width: 500, maxHeight: "92vh", overflowY: "auto", boxShadow: "0 12px 48px rgba(0,0,0,0.18)" }}>
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", padding: "18px 24px", borderBottom: "0.5px solid #eee" }}>
          <span style={{ fontSize: 15, fontWeight: 600, color: "#111" }}>{title}</span>
          <button onClick={onClose} style={{ ...btnGhost, padding: "4px 10px" }}>✕</button>
        </div>
        <div style={{ padding: "24px" }}>
          {children}
          {onSubmit && (
            <div style={{ display: "flex", justifyContent: "flex-end", gap: 8, marginTop: 8, borderTop: "0.5px solid #f0f0f0", paddingTop: 18 }}>
              <button style={btnGhost} onClick={onClose}>Cancel</button>
              <button style={{ ...btnPrimary, opacity: loading ? 0.6 : 1 }} onClick={onSubmit} disabled={loading}>
                {loading ? "Saving…" : submitLabel}
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

function DataTable({ cols, rows, onEdit, onDelete, onView }) {
  return (
    <div style={{ background: "#fff", borderRadius: 12, border: "0.5px solid #e8e8e8", overflow: "hidden" }}>
      <table style={{ width: "100%", borderCollapse: "collapse", tableLayout: "fixed" }}>
        <thead>
          <tr style={{ background: "#fafafa" }}>
            {cols.map(c => (
              <th key={c.key + c.label} style={{ padding: "10px 16px", fontSize: 11, fontWeight: 600, textAlign: "left", color: "#999", letterSpacing: "0.07em", textTransform: "uppercase", borderBottom: "0.5px solid #eee", width: c.w }}>
                {c.label}
              </th>
            ))}
            <th style={{ padding: "10px 16px", width: 160, borderBottom: "0.5px solid #eee" }}></th>
          </tr>
        </thead>
        <tbody>
          {rows.length === 0 ? (
            <tr><td colSpan={cols.length + 1} style={{ padding: 36, textAlign: "center", color: "#ccc", fontSize: 13 }}>No records found</td></tr>
          ) : rows.map((row, i) => (
            <tr key={row.id ?? i}
              onMouseEnter={e => e.currentTarget.style.background = "#fafafa"}
              onMouseLeave={e => e.currentTarget.style.background = "transparent"}
              style={{ borderBottom: "0.5px solid #f4f4f4", transition: "background 0.1s" }}
            >
              {cols.map(c => (
                <td key={c.key + c.label} style={{ padding: "12px 16px", fontSize: 13, color: "#111", overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap" }}>
                  {c.render ? c.render(row[c.key], row) : (row[c.key] != null ? String(row[c.key]) : "—")}
                </td>
              ))}
              <td style={{ padding: "12px 16px" }}>
                <div style={{ display: "flex", gap: 6 }}>
                  {onView   && <button style={btnGhost}  onClick={() => onView(row)}>View</button>}
                  {onEdit   && <button style={btnGhost}  onClick={() => onEdit(row)}>Edit</button>}
                  {onDelete && <button style={btnDanger} onClick={() => onDelete(row.id)}>Delete</button>}
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function SectionHeader({ title, onAdd, singular }) {
  return (
    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-end", marginBottom: 24 }}>
      <div>
        <p style={{ fontSize: 11, fontWeight: 600, color: "#bbb", textTransform: "uppercase", letterSpacing: "0.08em", margin: "0 0 4px" }}>Admin Panel</p>
        <h2 style={{ fontSize: 22, fontWeight: 700, color: "#111", margin: 0 }}>{title}</h2>
      </div>
      <button style={btnPrimary} onClick={onAdd}>+ Add {singular}</button>
    </div>
  );
}

function DetailRow({ label, value }) {
  return (
    <div style={{ display: "flex", justifyContent: "space-between", padding: "11px 0", borderBottom: "0.5px solid #f4f4f4", fontSize: 13 }}>
      <span style={{ color: "#888" }}>{label}</span>
      <span style={{ fontWeight: 500 }}>{String(value ?? "—")}</span>
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────────────────
// PROFILES
// ─────────────────────────────────────────────────────────────────────────────
const blankProfile = () => ({ name: "", lastName: "", birthday: "", hours: "", degree: "BACHELOR", lectureship: false });

// FIX: ProfileForm extracted outside component to avoid remount on every render
function ProfileForm({ form, setForm, status }) {
  const f = k => e => setForm(p => ({ ...p, [k]: e.target.type === "checkbox" ? e.target.checked : e.target.value }));
  return (
    <>
      <StatusBar msg={status} />
      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "0 16px" }}>
        <Field label="First name"><input style={inp} value={form.name}     onChange={f("name")}     placeholder="John" /></Field>
        <Field label="Last name"> <input style={inp} value={form.lastName} onChange={f("lastName")} placeholder="Doe"  /></Field>
      </div>
      <Field label="Birthday"><input style={inp} type="date" value={form.birthday} onChange={f("birthday")} /></Field>
      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "0 16px" }}>
        <Field label="Hours"><input style={inp} type="number" value={form.hours} onChange={f("hours")} placeholder="0" min="0" /></Field>
        <Field label="Degree">
          <select style={inp} value={form.degree} onChange={f("degree")}>
            {DEGREES.map(d => <option key={d} value={d}>{d}</option>)}
          </select>
        </Field>
      </div>
      <Field label="Lectureship">
        <label style={{ display: "flex", alignItems: "center", gap: 8, fontSize: 13, color: "#333", cursor: "pointer", padding: "9px 0" }}>
          <input type="checkbox" checked={form.lectureship} onChange={f("lectureship")} style={{ width: 16, height: 16, accentColor: "#111" }} />
          Is a lecturer
        </label>
      </Field>
    </>
  );
}

function ProfilesSection() {
  const [items,    setItems]    = useState([]);
  const [modal,    setModal]    = useState(null);
  const [selected, setSelected] = useState(null);
  const [loading,  setLoading]  = useState(false);
  const [status,   setStatus]   = useState("");
  const [form,     setForm]     = useState(blankProfile);

  const userId = localStorage.getItem("userId") || "";

  const load = useCallback(async () => {
    try { const r = await api.get("/profiles"); setItems(r.data); }
    catch { setStatus("Error loading profiles"); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const openCreate = ()    => { setForm(blankProfile()); setStatus(""); setModal("create"); };
  const openEdit   = (row) => {
    setSelected(row);
    // FIX: include all fields
    setForm({ name: row.name, lastName: row.lastName, birthday: row.birthday ?? "", hours: row.hours ?? "", degree: row.degree, lectureship: !!row.lectureship });
    setStatus(""); setModal("edit");
  };
  const openView = async (row) => {
    try { const r = await api.get(`/profiles/${row.id}`); setSelected(r.data); setModal("view"); }
    catch { setStatus("Error fetching profile"); }
  };

  const handleCreate = async () => {
    setLoading(true); setStatus("");
    try {
      await api.post("/profiles", { userId: Number(userId), name: form.name, lastName: form.lastName, localDate: form.birthday, hours: Number(form.hours), degree: form.degree, lectureship: form.lectureship });
      setModal(null); load();
    } catch { setStatus("Error creating profile"); }
    setLoading(false);
  };

  const handleUpdate = async () => {
    setLoading(true); setStatus("");
    try {
      await api.put(`/profiles/${selected.id}`, { userId: Number(userId), name: form.name, lastName: form.lastName, localDate: form.birthday, hours: Number(form.hours), degree: form.degree, lectureship: form.lectureship });
      setModal(null); load();
    } catch { setStatus("Error updating profile"); }
    setLoading(false);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this profile?")) return;
    try { await api.delete(`/profiles/${id}`); load(); }
    catch { setStatus("Error deleting profile"); }
  };

  const cols = [
    { key: "id",          label: "ID",       w: 60 },
    { key: "name",        label: "Full name", render: (v, r) => `${v} ${r.lastName}` },
    { key: "degree",      label: "Degree",    w: 120, render: v => <Badge color="#185FA5" bg="#E6F1FB">{v}</Badge> },
    { key: "hours",       label: "Hours",     w: 80  },
    { key: "lectureship", label: "Lecturer",  w: 90,  render: v => v ? <Badge color="#065f46" bg="#d1fae5">Yes</Badge> : <Badge color="#555" bg="#f0f0f0">No</Badge> },
    { key: "birthday",    label: "Birthday",  w: 110 },
  ];

  return (
    <div>
      <SectionHeader title="Profiles" singular="Profile" onAdd={openCreate} />
      <StatusBar msg={!modal ? status : ""} />
      <DataTable cols={cols} rows={items} onEdit={openEdit} onDelete={handleDelete} onView={openView} />

      {modal === "create" && (
        <Modal title="Create profile" onClose={() => setModal(null)} onSubmit={handleCreate} submitLabel="Create" loading={loading}>
          <ProfileForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
      {modal === "edit" && (
        <Modal title="Edit profile" onClose={() => setModal(null)} onSubmit={handleUpdate} submitLabel="Update" loading={loading}>
          <ProfileForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
      {modal === "view" && selected && (
        <Modal title="Profile details" onClose={() => setModal(null)}>
          <div style={{ display: "flex", alignItems: "center", gap: 16, marginBottom: 20 }}>
            {selected.profileImage
              ? <img src={selected.profileImage} alt="avatar" style={{ width: 56, height: 56, borderRadius: "50%", objectFit: "cover", border: "0.5px solid #eee" }} />
              : (
                <div style={{ width: 56, height: 56, borderRadius: "50%", background: "#E1F5EE", display: "flex", alignItems: "center", justifyContent: "center", fontSize: 20, fontWeight: 700, color: "#0F6E56" }}>
                  {selected.name?.[0]}{selected.lastName?.[0]}
                </div>
              )}
            <div>
              <p style={{ fontSize: 17, fontWeight: 600, margin: 0 }}>{selected.name} {selected.lastName}</p>
              <p style={{ fontSize: 13, color: "#888", margin: "3px 0 0" }}>ID: {selected.id}</p>
            </div>
          </div>
          <DetailRow label="Birthday"  value={selected.birthday} />
          <DetailRow label="Hours"     value={selected.hours} />
          <DetailRow label="Degree"    value={selected.degree} />
          <DetailRow label="Lecturer"  value={selected.lectureship ? "Yes" : "No"} />
        </Modal>
      )}
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────────────────
// GROUPS
// ─────────────────────────────────────────────────────────────────────────────

// FIX: GroupForm extracted outside component
function GroupForm({ form, setForm, status }) {
  const f = k => e => setForm(p => ({ ...p, [k]: e.target.value }));
  return (
    <>
      <StatusBar msg={status} />
      <Field label="Group name"><input style={inp} value={form.name} onChange={f("name")} placeholder="e.g. CS-101" /></Field>
      <Field label="Enrolled year"><input style={inp} type="number" value={form.enrolledYear} onChange={f("enrolledYear")} /></Field>
      {/* FIX: size field included */}
      <Field label="Group size"><input style={inp} type="number" value={form.size} onChange={f("size")} min="1" /></Field>
      <Field label="Direction">
        <div style={{ display: "grid", gridTemplateColumns: "repeat(3,1fr)", gap: 8 }}>
          {DIRECTIONS.map(d => {
            const active = form.direction === d;
            const ds = DIR_STYLE[d] || {};
            return (
              <button key={d} type="button"
                onClick={() => setForm(p => ({ ...p, direction: d }))}
                style={{ padding: "9px 6px", fontSize: 12, fontWeight: active ? 600 : 400, borderRadius: 8, cursor: "pointer",
                  border: active ? `2px solid ${ds.color}` : "0.5px solid #d0d0d0",
                  background: active ? ds.bg : "#fff", color: active ? ds.color : "#555" }}>
                {d.replace("_", " ")}
              </button>
            );
          })}
        </div>
      </Field>
    </>
  );
}

// FIX: blank includes size
const blankGroup = () => ({ name: "", enrolledYear: new Date().getFullYear(), direction: "BIG_DATA", size: 20 });

function GroupsSection() {
  const [items,    setItems]    = useState([]);
  const [modal,    setModal]    = useState(null);
  const [selected, setSelected] = useState(null);
  const [loading,  setLoading]  = useState(false);
  const [status,   setStatus]   = useState("");
  const [form,     setForm]     = useState(blankGroup);

  const load = useCallback(async () => {
    try { const r = await api.get("/groups"); setItems(r.data); }
    catch { setStatus("Error loading groups"); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const openCreate = () => { setForm(blankGroup()); setStatus(""); setModal("create"); };
  const openEdit   = (row) => {
    setSelected(row);
    // FIX: size included from row
    setForm({ name: row.name, enrolledYear: row.enrolledYear, direction: row.direction, size: row.size ?? 20 });
    setStatus(""); setModal("edit");
  };
  const openView = async (row) => {
    try { const r = await api.get(`/groups/${row.id}`); setSelected(r.data); setModal("view"); }
    catch { setStatus("Error fetching group"); }
  };

  const handleCreate = async () => {
    setLoading(true); setStatus("");
    try {
      // FIX: size sent to server
      await api.post("/groups", { name: form.name, enrolledYear: Number(form.enrolledYear), direction: form.direction, size: Number(form.size) });
      setModal(null); load();
    } catch { setStatus("Error creating group"); }
    setLoading(false);
  };

  const handleUpdate = async () => {
    setLoading(true); setStatus("");
    try {
      // FIX: size sent to server
      await api.put(`/groups/${selected.id}`, { name: form.name, enrolledYear: Number(form.enrolledYear), direction: form.direction, size: Number(form.size) });
      setModal(null); load();
    } catch { setStatus("Error updating group"); }
    setLoading(false);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this group?")) return;
    try { await api.delete(`/groups/${id}`); load(); }
    catch { setStatus("Error deleting group"); }
  };

  const cols = [
    { key: "id",           label: "ID",       w: 60  },
    { key: "name",         label: "Name"              },
    { key: "size",         label: "Size",      w: 80  },
    { key: "enrolledYear", label: "Year",      w: 90  },
    { key: "direction",    label: "Direction", w: 150, render: v => { const ds = DIR_STYLE[v] || {}; return <Badge color={ds.color} bg={ds.bg}>{v?.replace("_", " ")}</Badge>; } },
  ];

  return (
    <div>
      <SectionHeader title="Groups" singular="Group" onAdd={openCreate} />
      <StatusBar msg={!modal ? status : ""} />
      <DataTable cols={cols} rows={items} onEdit={openEdit} onDelete={handleDelete} onView={openView} />

      {modal === "create" && (
        <Modal title="Create group" onClose={() => setModal(null)} onSubmit={handleCreate} submitLabel="Create" loading={loading}>
          <GroupForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
      {modal === "edit" && (
        <Modal title="Edit group" onClose={() => setModal(null)} onSubmit={handleUpdate} submitLabel="Update" loading={loading}>
          <GroupForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
      {modal === "view" && selected && (
        <Modal title="Group details" onClose={() => setModal(null)}>
          <DetailRow label="ID"           value={selected.id} />
          <DetailRow label="Name"         value={selected.name} />
          <DetailRow label="Size"         value={selected.size} />
          <DetailRow label="Enrolled year"value={selected.enrolledYear} />
          <DetailRow label="Direction"    value={selected.direction} />
        </Modal>
      )}
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────────────────
// SUBJECTS
// ─────────────────────────────────────────────────────────────────────────────

// FIX: SubjectForm extracted outside component
function SubjectForm({ form, setForm, status }) {
  const f = k => e => setForm(p => ({ ...p, [k]: e.target.value }));
  return (
    <>
      <StatusBar msg={status} />
      <Field label="Subject name"><input style={inp} value={form.name} onChange={f("name")} placeholder="e.g. Mathematics" /></Field>
      <Field label="Credits"><input style={inp} type="number" value={form.credits} onChange={f("credits")} min="1" /></Field>
      {/* ✅ добавить */}
      <Field label="Hours"><input style={inp} type="number" value={form.hours} onChange={f("hours")} min="0" /></Field>
      <Field label="Type">
        <select style={inp} value={form.type} onChange={f("type")}>
          {["LECTURE", "SEMINAR", "LABORATORY"].map(t => <option key={t} value={t}>{t}</option>)}
        </select>
      </Field>
    </>
  );
}

const blankSubject = () => ({ name: "", credits: "", hours: "", type: "LECTURE" });

function SubjectsSection() {
  const [items,    setItems]    = useState([]);
  const [modal,    setModal]    = useState(null);
  const [selected, setSelected] = useState(null);
  const [loading,  setLoading]  = useState(false);
  const [status,   setStatus]   = useState("");
  const [form,     setForm]     = useState(blankSubject);

  const load = useCallback(async () => {
    try { const r = await api.get("/subjects"); setItems(r.data); }
    catch { setStatus("Error loading subjects"); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const openCreate = ()    => { setForm(blankSubject()); setStatus(""); setModal("create"); };
  const openEdit   = (row) => { setSelected(row); setForm({ name: row.name, credits: row.credits ,hours : row.hours, type : row.type}); setStatus(""); setModal("edit"); };
  const openView   = async (row) => {
    try { const r = await api.get(`/subjects/${row.id}`); setSelected(r.data); setModal("view"); }
    catch { setStatus("Error fetching subject"); }
  };

  const handleCreate = async () => {
    setLoading(true); setStatus("");
    try { await api.post("/subjects", { name: form.name, credits: Number(form.credits), hours: Number(form.hours), type: form.type }); }
    catch { setStatus("Error creating subject"); }
    setLoading(false);
  };

  const handleUpdate = async () => {
    setLoading(true); setStatus("");
    try { await api.put(`/subjects/${selected.id}`, { name: form.name, credits: Number(form.credits), hours: Number(form.hours), type: form.type }); }
    catch { setStatus("Error updating subject"); }
    setLoading(false);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this subject?")) return;
    try { await api.delete(`/subjects/${id}`); load(); }
    catch { setStatus("Error deleting subject"); }
  };

  const cols = [
    { key: "id",      label: "ID",      w: 60  },
    { key: "name",    label: "Name"             },
    { key: "credits", label: "Credits",  w: 110, render: v => <Badge color="#534AB7" bg="#EEEDFE">{v} cr</Badge> },
    { key: "hours",    label: "hours"             },
    { key: "type",    label: "type"             },
  ];

  return (
    <div>
      <SectionHeader title="Subjects" singular="Subject" onAdd={openCreate} />
      <StatusBar msg={!modal ? status : ""} />
      <DataTable cols={cols} rows={items} onEdit={openEdit} onDelete={handleDelete} onView={openView} />

      {modal === "create" && (
        <Modal title="Create subject" onClose={() => setModal(null)} onSubmit={handleCreate} submitLabel="Create" loading={loading}>
          <SubjectForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
      {modal === "edit" && (
        <Modal title="Edit subject" onClose={() => setModal(null)} onSubmit={handleUpdate} submitLabel="Update" loading={loading}>
          <SubjectForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
      {modal === "view" && selected && (
        <Modal title="Subject details" onClose={() => setModal(null)}>
          <DetailRow label="ID"      value={selected.id} />
          <DetailRow label="Name"    value={selected.name} />
          <DetailRow label="Hours" value={selected.hours} />
          <DetailRow label="Type"  value={selected.type} />
        </Modal>
      )}
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────────────────
// ENROLLMENTS
// ─────────────────────────────────────────────────────────────────────────────


function EnrollmentForm({ form, setForm, status, subjects, groups }) {
  const f = k => e => setForm(p => ({ ...p, [k]: e.target.value }));

  const toggleGroup = (id) => {
    const sid = String(id);
    setForm(p => {
      const current = p.groupsId ?? [];
      return {
        ...p,
        groupsId: current.includes(sid)
          ? current.filter(g => g !== sid)
          : [...current, sid]
      };
    });
  };

  const selectedGroups = form.groupsId ?? [];

  return (
    <>
      <StatusBar msg={status} />
      <Field label="Subject">
        <select style={inp} value={form.subjectId} onChange={f("subjectId")}>
          <option value="">Select a subject…</option>
          {subjects.map(s => <option key={s.id} value={s.id}>{s.name} ({s.credits} cr)</option>)}
        </select>
      </Field>
      <Field label="Groups">
        <div style={{ display: "flex", flexDirection: "column", gap: 6 }}>
          {groups.map(g => {
            const active = selectedGroups.includes(String(g.id));
            return (
              <label key={g.id} style={{ display: "flex", alignItems: "center", gap: 8, fontSize: 13, cursor: "pointer" }}>
                <input
                  type="checkbox"
                  checked={active}
                  onChange={() => toggleGroup(g.id)}
                  style={{ width: 15, height: 15, accentColor: "#111" }}
                />
                {g.name} — {g.direction}
              </label>
            );
          })}
        </div>
      </Field>
    </>
  );
}

const blankEnrollment = () => ({ subjectId: "", groupsId: [] });

function EnrollmentsSection() {
  const [items,    setItems]    = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [groups,   setGroups]   = useState([]);
  const [modal,    setModal]    = useState(null);
  const [selected, setSelected] = useState(null);
  const [loading,  setLoading]  = useState(false);
  const [status,   setStatus]   = useState("");
  const [form,     setForm]     = useState(blankEnrollment);

  const load = useCallback(async () => {
    try {
      const [er, sr, gr] = await Promise.all([
        api.get("/enrollments"),
        api.get("/subjects"),
        api.get("/groups"),
      ]);
      setItems(er.data); setSubjects(sr.data); setGroups(gr.data);
    } catch { setStatus("Error loading enrollments"); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const openCreate = () => { setForm(blankEnrollment()); setStatus(""); setModal("create"); };
  // FIX: убраны лишние });
  const openEdit = (row) => {
    setSelected(row);
    setForm({ subjectId: String(row.subjectId), groupsId: row.groupId ?? [] });
    setStatus("");
    setModal("edit");
  };
  const openView = async (row) => {
    try { const r = await api.get(`/enrollments/${row.id}`); setSelected(r.data); setModal("view"); }
    catch { setStatus("Error fetching enrollment"); }
  };

  const handleCreate = async () => {
    setLoading(true); setStatus("");
    // FIX: убраны лишние });
    try {
      await api.post("/enrollments", { subjectId: Number(form.subjectId), groupsId: form.groupsId.map(Number) });
      setModal(null); load();
    } catch { setStatus("Error creating enrollment"); }
    setLoading(false);
  };

  const handleUpdate = async () => {
    setLoading(true); setStatus("");
    try {
      await api.put(`/enrollments/${selected.id}`, { subjectId: Number(form.subjectId), groupsId: form.groupsId.map(Number) });
      setModal(null); load();
    } catch { setStatus("Error updating enrollment"); }
    setLoading(false);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this enrollment?")) return;
    try { await api.delete(`/enrollments/${id}`); load(); }
    catch { setStatus("Error deleting enrollment"); }
  };

  const subjectName = id => subjects.find(s => String(s.id) === String(id))?.name ?? `Subject #${id}`;
  const groupName   = id => groups.find(g => String(g.id) === String(id))?.name   ?? `Group #${id}`;

  const cols = [
    { key: "id",        label: "ID",      w: 60 },
    { key: "subjectId", label: "Subject", render: v => subjectName(v) },
    { key: "groupId",   label: "Groups",  render: v => Array.isArray(v) ? v.map(id => groupName(id)).join(", ") : groupName(v) },
    { key: "subjectId", label: "Sub. ID", w: 90, render: v => <Badge color="#854F0B" bg="#FAEEDA">{v}</Badge> },
    { key: "groupId",   label: "Grp. IDs", w: 120, render: v => Array.isArray(v) ? v.join(", ") : v },
  ];

  return (
    <div>
      <SectionHeader title="Enrollments" singular="Enrollment" onAdd={openCreate} />
      <StatusBar msg={!modal ? status : ""} />
      <DataTable cols={cols} rows={items} onEdit={openEdit} onDelete={handleDelete} onView={openView} />

      {modal === "create" && (
        <Modal title="Create enrollment" onClose={() => setModal(null)} onSubmit={handleCreate} submitLabel="Create" loading={loading}>
          <EnrollmentForm form={form} setForm={setForm} status={status} subjects={subjects} groups={groups} />
        </Modal>
      )}
      {modal === "edit" && (
        <Modal title="Edit enrollment" onClose={() => setModal(null)} onSubmit={handleUpdate} submitLabel="Update" loading={loading}>
          <EnrollmentForm form={form} setForm={setForm} status={status} subjects={subjects} groups={groups} />
        </Modal>
      )}
      {modal === "view" && selected && (
        <Modal title="Enrollment details" onClose={() => setModal(null)}>
          <DetailRow label="ID"         value={selected.id} />
          <DetailRow label="Subject"    value={subjectName(selected.subjectId)} />
          <DetailRow label="Groups"     value={Array.isArray(selected.groupId) ? selected.groupId.map(id => groupName(id)).join(", ") : groupName(selected.groupId)} />
          <DetailRow label="Subject ID" value={selected.subjectId} />
          <DetailRow label="Group IDs"  value={Array.isArray(selected.groupId) ? selected.groupId.join(", ") : selected.groupId} />
        </Modal>
      )}
    </div>
  );
}

// ─── Constants ────────────────────────────────────────────────────────────────
const ACTIVITY_TYPES = ["LECTURE", "SEMINAR", "LABORATORY"];

const TYPE_STYLE = {
  LECTURE:    { color: "#185FA5", bg: "#E6F1FB" },
  SEMINAR:    { color: "#1D9E75", bg: "#E1F5EE" },
  LABORATORY: { color: "#7F77DD", bg: "#EEEDFE" },
};

// ─── LessonForm ───────────────────────────────────────────────────────────────
function LessonForm({ form, setForm, status, enrollments, subjects, groups }) {
  const f = k => e => setForm(p => ({ ...p, [k]: e.target.value }));

  const subjectName = id => {
    if (!subjects.length) return `Subject #${id}`;
    return subjects.find(s => String(s.id) === String(id))?.name ?? `Subject #${id}`;
  };

  const groupNames = ids => {
    if (!groups.length) return "—";
    return Array.isArray(ids)
      ? ids.map(id => groups.find(g => String(g.id) === String(id))?.name ?? `Group #${id}`).join(", ")
      : `Group #${ids}`;
  };

  return (
    <>
      <StatusBar msg={status} />
      <Field label="Enrollment">
        <select style={inp} value={form.enrollmentId} onChange={f("enrollmentId")}>
          <option value="">Select an enrollment…</option>
          {enrollments.map(e => (
            <option key={e.id} value={e.id}>
              #{e.id} — {subjectName(e.subjectId)} / {groupNames(e.groupId)}
            </option>
          ))}
        </select>
      </Field>
    </>
  );
}

const blankLesson = () => ({ enrollmentId: "" });

// ─── LessonsSection ───────────────────────────────────────────────────────────
function LessonsSection() {
  const [items,       setItems]       = useState([]);
  const [enrollments, setEnrollments] = useState([]);
  const [subjects,    setSubjects]    = useState([]);
  const [groups,      setGroups]      = useState([]);
  const [modal,       setModal]       = useState(null);
  const [selected,    setSelected]    = useState(null);
  const [loading,     setLoading]     = useState(false);
  const [status,      setStatus]      = useState("");
  const [form,        setForm]        = useState(blankLesson);

  const load = useCallback(async () => {
    try {
      const [lr, er, sr, gr] = await Promise.all([
        api.get("/lessons"),
        api.get("/enrollments"),
        api.get("/subjects"),
        api.get("/groups"),
      ]);
      setItems(lr.data);
      setEnrollments(er.data);
      setSubjects(sr.data);
      setGroups(gr.data);
    } catch { setStatus("Error loading lessons"); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const openCreate = () => { setForm(blankLesson()); setStatus(""); setModal("create"); };
  const openEdit   = (row) => {
    setSelected(row);
    setForm({ enrollmentId: String(row.enrollmentId ?? "") });
    setStatus(""); setModal("edit");
  };
  const openView = async (row) => {
    try { const r = await api.get(`/lessons/${row.id}`); setSelected(r.data); setModal("view"); }
    catch { setStatus("Error fetching lesson"); }
  };

  const handleCreate = async () => {
    setLoading(true); setStatus("");
    try {
      await api.post("/lessons", { enrollmentId: Number(form.enrollmentId) });
      setModal(null); load();
    } catch { setStatus("Error creating lesson"); }
    setLoading(false);
  };

  const handleUpdate = async () => {
    setLoading(true); setStatus("");
    try {
      await api.put(`/lessons/${selected.id}`, { enrollmentId: Number(form.enrollmentId) });
      setModal(null); load();
    } catch { setStatus("Error updating lesson"); }
    setLoading(false);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this lesson?")) return;
    try { await api.delete(`/lessons/${id}`); load(); }
    catch { setStatus("Error deleting lesson"); }
  };

  // subjectName больше не нужен — приходит строкой из response
  const groupName = id => groups.find(g => String(g.id) === String(id))?.name ?? `Group #${id}`;

  const cols = [
    { key: "id",          label: "ID",       w: 60 },
    { key: "subjectName", label: "Subject"          },  // ✅ строка напрямую
    { key: "hours",       label: "Hours",    w: 80  },
    { key: "type",        label: "Type",     w: 130, render: v => {
      const ts = TYPE_STYLE[v] || {};
      return <Badge color={ts.color} bg={ts.bg}>{v}</Badge>;
    }},
    { key: "groupsId",   label: "Groups",   render: v => Array.isArray(v) ? v.map(id => groupName(id)).join(", ") : (v ?? "—") },
    { key: "groupId",    label: "Group",    w: 90, render: v => v ? groupName(v) : "—" },
    { key: "subgroupId", label: "Subgroup", w: 90 },
  ];

  return (
    <div>
      <SectionHeader title="Lessons" singular="Lesson" onAdd={openCreate} />
      <StatusBar msg={!modal ? status : ""} />
      <DataTable cols={cols} rows={items} onEdit={openEdit} onDelete={handleDelete} onView={openView} />

      {modal === "create" && (
        <Modal title="Create lesson" onClose={() => setModal(null)} onSubmit={handleCreate} submitLabel="Create" loading={loading}>
          <LessonForm
            form={form}
            setForm={setForm}
            status={status}
            enrollments={enrollments}
            subjects={subjects}
            groups={groups}
          />
        </Modal>
      )}
      {modal === "edit" && (
        <Modal title="Edit lesson" onClose={() => setModal(null)} onSubmit={handleUpdate} submitLabel="Update" loading={loading}>
          <LessonForm
            form={form}
            setForm={setForm}
            status={status}
            enrollments={enrollments}
            subjects={subjects}
            groups={groups}
          />
        </Modal>
      )}
      {modal === "view" && selected && (
        <Modal title="Lesson details" onClose={() => setModal(null)}>
          <DetailRow label="ID"          value={selected.id} />
          <DetailRow label="Subject"     value={selected.subjectName} />  {/* ✅ строка напрямую */}
          <DetailRow label="Hours"       value={selected.hours} />
          <DetailRow label="Type"        value={selected.type} />
          <DetailRow label="Groups"      value={Array.isArray(selected.groupsId) ? selected.groupsId.map(id => groupName(id)).join(", ") : "—"} />
          <DetailRow label="Group"       value={selected.groupId ? groupName(selected.groupId) : "—"} />
          <DetailRow label="Subgroup ID" value={selected.subgroupId ?? "—"} />
        </Modal>
      )}
    </div>
  );
}

// ─── AssignmentsSection ───────────────────────────────────────────────────────
function AssignmentsSection() {
  const [lessons,     setLessons]     = useState([]);
  const [profiles,    setProfiles]    = useState([]);
  const [assignments, setAssignments] = useState([]);
  const [selected,    setSelected]    = useState(null);
  const [teacherId,   setTeacherId]   = useState("");
  const [status,      setStatus]      = useState("");
  const [loading,     setLoading]     = useState(false);
  const [groups,      setGroups]      = useState([]);

  const load = useCallback(async () => {
    try {
      const [ar, lr, pr, gr] = await Promise.all([
        api.get("/assignments"),
        api.get("/lessons"),
        api.get("/profiles"),
        api.get("/groups"),
      ]);
      console.log(ar.data)
      setGroups(gr.data);
      setAssignments(ar.data);
      setLessons(lr.data);
      setProfiles(pr.data.filter(p => p.lectureship === true));
      setSelected(prev => prev
        ? lr.data.find(l => String(l.id) === String(prev.id)) ?? prev
        : null
      );
    } catch { setStatus("Error loading assignments"); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const teacherName = id => {
    const t = profiles.find(p => String(p.id) === String(id));
    return t ? `${t.name} ${t.lastName}` : `Teacher #${id}`;
  };

  const lessonAssignments = selected
    ? assignments.filter(a => String(a.lessonId) === String(selected.id))
    : [];

  const availableTeachers = profiles.filter(p =>
    !lessonAssignments.some(a => String(a.profileId) === String(p.id))
  );

  const handleAssign = async () => {
    if (!teacherId || !selected) return;
    setLoading(true);
    try {
      await api.post("/assignments", { profileId: Number(teacherId), lessonId: Number(selected.id) });
      setTeacherId("");
      await load();
    } catch { setStatus("Error assigning teacher"); }
    setLoading(false);
  };

  const handleRemove = async (assignmentId) => {
    if (!window.confirm("Remove this assignment?")) return;
    try {
      await api.delete(`/assignments/${assignmentId}`);
      load();
    } catch { setStatus("Error removing assignment"); }
  };

  const groupName  = id  => groups.find(g => String(g.id) === String(id))?.name ?? `Group #${id}`;
  const groupNames = ids => Array.isArray(ids) && ids.length ? ids.map(id => groupName(id)).join(", ") : "—";

  const lessonGroups = (lesson) => {
    switch (lesson.type) {
      case "LECTURE":    return groupNames(lesson.groupsId);
      case "SEMINAR":    return lesson.groupId ? groupName(lesson.groupId) : "—";
      case "LABORATORY": return lesson.groupId && lesson.subgroupId
        ? `${groupName(lesson.groupId)} / Sub #${lesson.subgroupId}`
        : lesson.groupId ? groupName(lesson.groupId) : "—";
      default: return "—";
    }
  };

  return (
    <div>
      <SectionHeader title="Assignments" singular="Assignment" onAdd={null} />
      <StatusBar msg={status} />

      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 24 }}>

        {/* ── Left: Lessons list ── */}
        <div style={{ background: "#fff", borderRadius: 12, border: "0.5px solid #e8e8e8", overflow: "hidden" }}>
          <div style={{ padding: "12px 16px", borderBottom: "0.5px solid #eee", fontSize: 11, fontWeight: 600, color: "#999", textTransform: "uppercase", letterSpacing: "0.07em" }}>
            Lessons
          </div>
          {lessons.length === 0 ? (
            <p style={{ padding: 24, color: "#ccc", fontSize: 13, textAlign: "center" }}>No lessons</p>
          ) : lessons.map(l => {
            const isActive = selected?.id === l.id;
            const count = assignments.filter(a => String(a.lessonId) === String(l.id)).length;
            return (
              <div key={l.id}
                onClick={() => { setSelected(l); setTeacherId(""); }}
                style={{
                  padding: "12px 16px", cursor: "pointer", fontSize: 13,
                  borderBottom: "0.5px solid #f4f4f4",
                  background: isActive ? "#f0f7ff" : "transparent",
                  borderLeft: isActive ? "3px solid #378ADD" : "3px solid transparent",
                }}
              >
                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                  <div>
                    <span style={{ fontWeight: isActive ? 600 : 400 }}>{l.subjectName}</span>
                    <span style={{ marginLeft: 8, fontSize: 11, color: "#aaa" }}>#{l.id}</span>
                  </div>
                  <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
                    {(() => { const ts = TYPE_STYLE[l.type] || {}; return <Badge color={ts.color} bg={ts.bg}>{l.type}</Badge>; })()}
                    <span style={{ fontSize: 11, color: "#888" }}>{count} teacher{count !== 1 ? "s" : ""}</span>
                  </div>
                </div>
                <div style={{ fontSize: 11, color: "#888", marginTop: 4 }}>
                  {lessonGroups(l)}
                </div>
              </div>
            );
          })}
        </div>

        {/* ── Right: Teachers for selected lesson ── */}
        <div style={{ background: "#fff", borderRadius: 12, border: "0.5px solid #e8e8e8", overflow: "hidden" }}>
          <div style={{ padding: "12px 16px", borderBottom: "0.5px solid #eee", fontSize: 11, fontWeight: 600, color: "#999", textTransform: "uppercase", letterSpacing: "0.07em" }}>
            {selected ? `Teachers — ${selected.subjectName} #${selected.id}` : "Select a lesson"}
          </div>

          {!selected ? (
            <p style={{ padding: 24, color: "#ccc", fontSize: 13, textAlign: "center" }}>← Select a lesson</p>
          ) : (
            <div style={{ padding: 16 }}>

              {lessonAssignments.length === 0 ? (
                <p style={{ color: "#ccc", fontSize: 13, marginBottom: 16 }}>No teachers assigned</p>
              ) : lessonAssignments.map(a => (
                <div key={a.id} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", padding: "8px 0", borderBottom: "0.5px solid #f4f4f4" }}>
                  <div>
                    <span style={{ fontSize: 13 }}>{teacherName(a.profileId)}</span>
                    <span style={{ fontSize: 11, color: "#aaa", marginLeft: 8 }}>{a.duration} hour</span>
                  </div>
                  <button style={btnDanger} onClick={() => handleRemove(a.id)}>Remove</button>
                </div>
              ))}

              {availableTeachers.length > 0 && (
                <div style={{ display: "flex", gap: 8, marginTop: 16 }}>
                  <select style={{ ...inp, flex: 1 }} value={teacherId} onChange={e => setTeacherId(e.target.value)}>
                    <option value="">+ Assign teacher…</option>
                    {availableTeachers.map(t => (
                      <option key={t.id} value={t.id}>{t.name} {t.lastName}</option>
                    ))}
                  </select>
                  <button
                    style={{ ...btnPrimary, opacity: (!teacherId || loading) ? 0.6 : 1 }}
                    onClick={handleAssign}
                    disabled={!teacherId || loading}
                  >
                    Assign
                  </button>
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

// ─── ClassRoomsSection ───────────────────────────────────────────────────────

function ClassRoomForm({ form, setForm, status }) {
  const f = k => e => setForm(p => ({ ...p, [k]: e.target.value }));
  return (
    <>
      <StatusBar msg={status} />
      <Field label="Room number">
        <input style={inp} type="number" value={form.number} onChange={f("number")} placeholder="e.g. 301" min="1" />
      </Field>
      <Field label="Capacity">
        <input style={inp} type="number" value={form.size} onChange={f("size")} placeholder="e.g. 30" min="1" />
      </Field>
    </>
  );
}

const blankClassRoom = () => ({ number: "", size: "" });

function ClassRoomsSection() {
  const [items,       setItems]       = useState([]);
  const [assignments, setAssignments] = useState([]);
  const [lessons,     setLessons]     = useState([]);
  const [groups,      setGroups]      = useState([]);
  const [modal,       setModal]       = useState(null);
  const [selected,    setSelected]    = useState(null);
  const [loading,     setLoading]     = useState(false);
  const [status,      setStatus]      = useState("");
  const [form,        setForm]        = useState(blankClassRoom);
  const [nonActive,   setNonActive]   = useState([]);

  const [bookingSlot,  setBookingSlot]  = useState(null);
  const [assignmentId, setAssignmentId] = useState("");

  const nonActiveAssignments = assignments.filter(a => !a.isActive);

  const load = useCallback(async () => {
    try {
      const [cr, ar, lr, gr,nar] = await Promise.all([
        api.get("/classrooms"),
        api.get("/assignments"),
        api.get("/lessons"),
        api.get("/groups"),
        api.get("/assignments/nonactive")
      ]);
      setItems(cr.data);
      setAssignments(ar.data);
      setLessons(lr.data);
      setGroups(gr.data);
      setNonActive(nar.data);
    } catch { setStatus("Error loading classrooms"); }
  }, []);
  useEffect(() => { load(); }, [load]);

  const openCreate = () => { setForm(blankClassRoom()); setStatus(""); setModal("create"); };
  const openEdit   = (row) => { setSelected(row); setForm({ number: row.number, size: row.size }); setStatus(""); setModal("edit"); };

  const handleCreate = async () => {
    setLoading(true); setStatus("");
    try {
      await api.post("/classrooms", { number: Number(form.number), size: Number(form.size) });
      setModal(null); load();
    } catch { setStatus("Error creating classroom"); }
    setLoading(false);
  };

  const handleUpdate = async () => {
    setLoading(true); setStatus("");
    try {
      await api.put(`/classrooms/${selected.id}`, { number: Number(form.number), size: Number(form.size) });
      setModal(null); load();
    } catch { setStatus("Error updating classroom"); }
    setLoading(false);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this classroom?")) return;
    try { await api.delete(`/classrooms/${id}`); load(); }
    catch { setStatus("Error deleting classroom"); }
  };

  const handleBook = async () => {
    if (!assignmentId || !bookingSlot) return;
    setLoading(true);
    try {
      await api.post("/classrooms/book", {
        classRoomId:     bookingSlot.classRoomId,
        assignmentId:    Number(assignmentId),
        day:             bookingSlot.weekday,
        lessonStartTime: bookingSlot.start,
      });
      setBookingSlot(null);
      setAssignmentId("");
      load();
    } catch { setStatus("Error booking classroom"); }
    setLoading(false);
  };

  const groupName = id => groups.find(g => String(g.id) === String(id))?.name ?? `Group #${id}`;

  const getBookingLabel = (bookedByAssignmentId) => {
    const assignment = assignments.find(a => String(a.id) === String(bookedByAssignmentId));
    if (!assignment) return "Booked";
    const lesson = lessons.find(l => String(l.id) === String(assignment.lessonId));
    if (!lesson) return "Booked";

    let groupLabel = "";
    switch (lesson.type) {
      case "LECTURE":
        groupLabel = (lesson.groupsId || []).map(id => groupName(id)).join(", ");
        break;
      case "SEMINAR":
        groupLabel = lesson.groupId ? groupName(lesson.groupId) : "";
        break;
      case "LABORATORY":
        groupLabel = lesson.groupId
          ? lesson.subgroupId
            ? `${groupName(lesson.groupId)} / Sub #${lesson.subgroupId}`
            : groupName(lesson.groupId)
          : "";
        break;
      default:
        groupLabel = "";
    }

    return  `${lesson.subjectName} · ${lesson.type}${groupLabel ? ` · ${groupLabel}` : ""}`;
  };

  const allSlots = [];
  const slotSet  = new Set();
  items.forEach(room => {
    (room.timeslots || []).forEach(t => {
      const key = `${t.weekday}__${t.start}`;
      if (!slotSet.has(key)) {
        slotSet.add(key);
        allSlots.push({ weekday: t.weekday, start: t.start, finish: t.finish, key });
      }
    });
  });

  const WEEKDAY_ORDER = ["MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"];
  allSlots.sort((a, b) => {
    const di = WEEKDAY_ORDER.indexOf(a.weekday) - WEEKDAY_ORDER.indexOf(b.weekday);
    return di !== 0 ? di : a.start.localeCompare(b.start);
  });

  const getSlot = (room, weekday, start) =>
    (room.timeslots || []).find(t => t.weekday === weekday && t.start === start);

  const STATE_STYLE = {
    AVAILABLE:     { color: "#065f46", bg: "#d1fae5", label: "Free" },
    BOOKED:        { color: "#b91c1c", bg: "#fee2e2" },
    NOT_AVAILABLE: { color: "#6b7280", bg: "#f3f4f6", label: "N/A" },
  };

  const headerCell = {
    padding: "10px 14px", fontSize: 11, fontWeight: 700, color: "#fff",
    background: "#1F3864", textAlign: "center", whiteSpace: "nowrap",
    border: "0.5px solid #2E4B7A", letterSpacing: "0.05em",
  };

  const rowLabelCell = {
    padding: "8px 12px", fontSize: 11, fontWeight: 600, color: "#555",
    background: "#f8f8f8", whiteSpace: "nowrap", border: "0.5px solid #e8e8e8",
    textAlign: "right", minWidth: 160,
  };

  return (
    <div>
      <SectionHeader title="Classrooms" singular="Classroom" onAdd={openCreate} />
      <StatusBar msg={status} />

      <div style={{ overflowX: "auto", borderRadius: 12, border: "0.5px solid #e8e8e8", background: "#fff" }}>
        <table style={{ borderCollapse: "collapse", width: "100%" }}>
          <thead>
            <tr>
              <th style={{ ...headerCell, background: "#111", minWidth: 160 }}>Timeslot / Room</th>
              {items.map(room => (
                <th key={room.id} style={{ ...headerCell, minWidth: 130 }}>
                  <div>Room #{room.number}</div>
                  <div style={{ fontSize: 10, fontWeight: 400, opacity: 0.8 }}>{room.size} seats</div>
                  <div style={{ display: "flex", gap: 4, justifyContent: "center", marginTop: 4 }}>
                    <button onClick={() => openEdit(room)}
                      style={{ fontSize: 10, padding: "2px 6px", borderRadius: 4, border: "none", background: "#fff3", color: "#fff", cursor: "pointer" }}>
                      Edit
                    </button>
                    <button onClick={() => handleDelete(room.id)}
                      style={{ fontSize: 10, padding: "2px 6px", borderRadius: 4, border: "none", background: "#ff4444", color: "#fff", cursor: "pointer" }}>
                      Del
                    </button>
                  </div>
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {allSlots.map((slot, i) => {
              const isNewDay = i === 0 || allSlots[i - 1].weekday !== slot.weekday;
              return (
                <>
                  {isNewDay && (
                    <tr key={`day-${slot.weekday}`}>
                      <td colSpan={items.length + 1} style={{
                        padding: "6px 14px", fontSize: 11, fontWeight: 700, color: "#fff",
                        background: "#2E75B6", letterSpacing: "0.08em", textTransform: "uppercase",
                      }}>
                        {slot.weekday}
                      </td>
                    </tr>
                  )}
                  <tr key={slot.key} style={{ background: i % 2 === 0 ? "#fff" : "#fafafa" }}>
                    <td style={rowLabelCell}>
                      {slot.start?.slice(0, 5)} – {slot.finish?.slice(0, 5)}
                    </td>
                    {items.map(room => {
                      const t         = getSlot(room, slot.weekday, slot.start);
                      const st        = t ? (STATE_STYLE[t.roomState] || STATE_STYLE.NOT_AVAILABLE) : null;
                      const isAvailable = t?.roomState === "AVAILABLE";
                      const isBooked    = t?.roomState === "BOOKED";

                      return (
                        <td key={room.id} style={{ border: "0.5px solid #f0f0f0", textAlign: "center", padding: 6 }}>
                          {t ? (
                            <div style={{ display: "flex", flexDirection: "column", alignItems: "center", gap: 4 }}>
                              <span style={{
                                display: "inline-block", padding: "3px 10px", borderRadius: 99,
                                fontSize: 11, fontWeight: 600, color: st.color, background: st.bg,
                                maxWidth: 160, textAlign: "center", whiteSpace: "normal", lineHeight: 1.4,
                              }}>
                                {isBooked
                                  ? getBookingLabel(t.bookedByAssignmentId)
                                  : st.label
                                }
                              </span>
                              {isAvailable && (
                                <button
                                  onClick={() => { setBookingSlot({ classRoomId: room.id, weekday: slot.weekday, start: slot.start }); setAssignmentId(""); setModal("book"); }}
                                  style={{ fontSize: 10, padding: "2px 8px", borderRadius: 4, border: "0.5px solid #2E75B6", background: "#E6F1FB", color: "#185FA5", cursor: "pointer" }}
                                >
                                  Book
                                </button>
                              )}
                            </div>
                          ) : (
                            <span style={{ color: "#ddd", fontSize: 11 }}>—</span>
                          )}
                        </td>
                      );
                    })}
                  </tr>
                </>
              );
            })}
          </tbody>
        </table>
      </div>

      {items.length === 0 && (
        <p style={{ textAlign: "center", color: "#ccc", fontSize: 13, marginTop: 32 }}>No classrooms yet</p>
      )}

      {modal === "book" && bookingSlot && (
        <Modal
          title={`Book slot — ${bookingSlot.weekday} ${bookingSlot.start?.slice(0, 5)}`}
          onClose={() => { setModal(null); setBookingSlot(null); }}
          onSubmit={handleBook}
          submitLabel="Book"
          loading={loading}
        >
          <StatusBar msg={status} />
          <Field label="Assignment">
            <select style={inp} value={assignmentId} onChange={e => setAssignmentId(e.target.value)}>
              <option value="">Select an assignment…</option>
              {nonActive.map(a => {
                const lesson = lessons.find(l => String(l.id) === String(a.lessonId));
                return (
                  <option key={a.id} value={a.id}>
                    {lesson ? `${lesson.subjectName} — ${lesson.type}` : `Lesson #${a.lessonId}`} / Assignment #{a.id}
                  </option>
                );
              })}
            </select>
          </Field>
        </Modal>
      )}

      {modal === "create" && (
        <Modal title="Create classroom" onClose={() => setModal(null)} onSubmit={handleCreate} submitLabel="Create" loading={loading}>
          <ClassRoomForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
      {modal === "edit" && (
        <Modal title="Edit classroom" onClose={() => setModal(null)} onSubmit={handleUpdate} submitLabel="Update" loading={loading}>
          <ClassRoomForm form={form} setForm={setForm} status={status} />
        </Modal>
      )}
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────────────────
// ROOT ADMIN PAGE
// ─────────────────────────────────────────────────────────────────────────────

// FIX: dynamic render instead of static object — prevents all sections mounting at once
const SECTION_MAP = {
  profiles:    <ProfilesSection />,
  groups:      <GroupsSection />,
  subjects:    <SubjectsSection />,
  enrollments: <EnrollmentsSection />,
  lessons: <LessonsSection />,
  assignments: <AssignmentsSection />,
  classrooms: <ClassRoomsSection />,
};

export default function AdminPage() {
  const [active, setActive] = useState("profiles");

  return (
    <div style={{ display: "flex", minHeight: "100vh", fontFamily: "'DM Sans', 'Geist', system-ui, sans-serif", background: "#f7f7f5" }}>

      {/* ── Sidebar ── */}
      <aside style={{ width: 220, background: "#fff", borderRight: "0.5px solid #e8e8e8", display: "flex", flexDirection: "column", flexShrink: 0, position: "sticky", top: 0, height: "100vh" }}>
        <div style={{ padding: "28px 20px 16px" }}>
          <p style={{ fontSize: 10, fontWeight: 700, color: "#ccc", letterSpacing: "0.12em", textTransform: "uppercase", margin: "0 0 4px" }}>Admin</p>
          <p style={{ fontSize: 18, fontWeight: 700, color: "#111", margin: 0 }}>Dashboard</p>
        </div>
        <div style={{ height: "0.5px", background: "#eee" }} />
        <nav style={{ padding: "12px 10px", display: "flex", flexDirection: "column", gap: 2, flex: 1 }}>
          {NAV.map(key => {
            const meta = NAV_META[key];
            const isActive = active === key;
            return (
              <button key={key} onClick={() => setActive(key)} style={{
                padding: "10px 14px", fontSize: 13, fontWeight: isActive ? 600 : 400,
                borderRadius: 8, border: "none", textAlign: "left", cursor: "pointer",
                background: isActive ? meta.color + "15" : "transparent",
                color: isActive ? meta.color : "#666",
                display: "flex", alignItems: "center", gap: 10, transition: "all 0.12s",
              }}>
                <span style={{ width: 7, height: 7, borderRadius: "50%", background: isActive ? meta.color : "#ddd", flexShrink: 0, transition: "background 0.12s" }} />
                {meta.label}
              </button>
            );
          })}
        </nav>
        <div style={{ padding: "16px 20px", borderTop: "0.5px solid #eee" }}>
          <p style={{ fontSize: 11, color: "#bbb", margin: 0 }}>User ID: {localStorage.getItem("userId") || "—"}</p>
        </div>
      </aside>

      {/* ── Main ── */}
      <main style={{ flex: 1, padding: "40px 48px", overflowY: "auto" }}>
        <div style={{ maxWidth: 980 }}>
          {/* FIX: render only active section */}
          {SECTION_MAP[active]}
        </div>
      </main>
    </div>
  );
}
