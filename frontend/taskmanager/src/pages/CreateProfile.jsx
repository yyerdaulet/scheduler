import React, { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

const DEGREES = [
  { value: "BACHELOR", label: "Bachelor" },
  { value: "MASTER",   label: "Master"   },
  { value: "PHD",      label: "PhD"      },
  { value: "DOCTOR",   label: "Doctor"   },
];

const inp = {
  width: "100%", padding: "9px 12px", fontSize: 13, borderRadius: 8,
  border: "0.5px solid #d0d0d0", background: "#fafafa", color: "#111",
  outline: "none", boxSizing: "border-box", fontFamily: "inherit",
};

const label = {
  display: "block", fontSize: 11, fontWeight: 600, color: "#888",
  marginBottom: 5, textTransform: "uppercase", letterSpacing: "0.06em",
};

const field = { marginBottom: 16 };

const divider = { height: "0.5px", background: "#eeeeee", margin: "20px 0" };

const sectionLabel = {
  fontSize: 11, fontWeight: 700, color: "#aaa", textTransform: "uppercase",
  letterSpacing: "0.09em", marginBottom: 16,
};

export default function ProfileForm() {
  const navigate = useNavigate();
  const userId   = localStorage.getItem("id");

  const [formData, setFormData] = useState({
    userId:      Number(userId),
    name:        "",
    lastName:    "",
    localDate:   "",
    hours:       "",
    degree:      "BACHELOR",
    lectureship: false,
  });

  const [loading, setLoading] = useState(false);
  const [error,   setError]   = useState("");

  const handleChange = e =>
    setFormData(p => ({ ...p, [e.target.name]: e.target.value }));

  const handleSubmit = async e => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      await api.post("/profiles", {
        ...formData,
        userId: Number(userId),
        hours:  Number(formData.hours),
      });
      navigate(`/profiles/${userId}`);
    } catch {
      setError("Failed to save profile. Please check your details and try again.");
      setLoading(false);
    }
  };

  // 5-step progress: name, lastName, localDate, hours, degree
  const filled = [formData.name, formData.lastName, formData.localDate, formData.hours, formData.degree]
    .filter(Boolean).length;

  return (
    <div style={{ minHeight: "100vh", background: "#f7f7f5", display: "flex", alignItems: "center", justifyContent: "center", padding: "48px 16px" }}>
      <div style={{ width: "100%", maxWidth: 500 }}>

        {/* Brand */}
        <div style={{ textAlign: "center", marginBottom: 28 }}>
          <div style={{ width: 48, height: 48, borderRadius: 12, background: "#fff", border: "0.5px solid #e0e0e0", display: "inline-flex", alignItems: "center", justifyContent: "center", fontSize: 22, marginBottom: 10 }}>
            🎓
          </div>
          <div style={{ fontSize: 17, fontWeight: 700, color: "#111" }}>ResearchHub</div>
          <div style={{ fontSize: 12, color: "#aaa", marginTop: 2 }}>Academic Profile Platform</div>
        </div>

        {/* Card */}
        <div style={{ background: "#fff", borderRadius: 14, border: "0.5px solid #e8e8e8", overflow: "hidden" }}>

          {/* Card header */}
          <div style={{ padding: "22px 28px 18px", borderBottom: "0.5px solid #f0f0f0" }}>
            <p style={{ margin: "0 0 4px", fontSize: 16, fontWeight: 600, color: "#111" }}>Set up your profile</p>
            <p style={{ margin: "0 0 14px", fontSize: 13, color: "#aaa" }}>Fill in your academic information</p>

            {/* Progress dots */}
            <div style={{ display: "flex", gap: 6 }}>
              {[0, 1, 2, 3, 4].map(i => (
                <div key={i} style={{
                  height: 4, flex: 1, borderRadius: 99,
                  background: i < filled ? "#111" : i === filled ? "#d0d0d0" : "#f0f0f0",
                  transition: "background 0.2s",
                }} />
              ))}
            </div>
          </div>

          {/* Form body */}
          <form onSubmit={handleSubmit} style={{ padding: "24px 28px" }}>

            {error && (
              <div style={{ padding: "10px 14px", borderRadius: 8, background: "#fef2f2", color: "#b91c1c", border: "0.5px solid #fca5a5", fontSize: 13, marginBottom: 20 }}>
                {error}
              </div>
            )}

            {/* Personal */}
            <p style={sectionLabel}>Personal Information</p>

            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "0 14px" }}>
              <div style={field}>
                <label style={label}>First name <sup style={{ color: "#e00" }}>*</sup></label>
                <input style={inp} type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Jane" required />
              </div>
              <div style={field}>
                <label style={label}>Last name <sup style={{ color: "#e00" }}>*</sup></label>
                <input style={inp} type="text" name="lastName" value={formData.lastName} onChange={handleChange} placeholder="Doe" required />
              </div>
            </div>

            <div style={field}>
              <label style={label}>Date of birth <sup style={{ color: "#e00" }}>*</sup></label>
              <input style={inp} type="date" name="localDate" value={formData.localDate} onChange={handleChange} required />
            </div>

            <div style={divider} />

            {/* Academic */}
            <p style={sectionLabel}>Academic Details</p>

            <div style={field}>
              <label style={label}>Hours <sup style={{ color: "#e00" }}>*</sup></label>
              <input style={inp} type="number" name="hours" value={formData.hours} onChange={handleChange} placeholder="e.g. 120" min="0" required />
            </div>

            <div style={field}>
              <label style={label}>Academic degree <sup style={{ color: "#e00" }}>*</sup></label>
              <div style={{ display: "grid", gridTemplateColumns: "repeat(4,1fr)", gap: 8 }}>
                {DEGREES.map(d => {
                  const active = formData.degree === d.value;
                  return (
                    <button key={d.value} type="button"
                      onClick={() => setFormData(p => ({ ...p, degree: d.value }))}
                      style={{
                        padding: "9px 6px", fontSize: 12, borderRadius: 8, cursor: "pointer",
                        fontWeight: active ? 600 : 400,
                        border: active ? "1.5px solid #111" : "0.5px solid #d0d0d0",
                        background: active ? "#111" : "#fafafa",
                        color: active ? "#fff" : "#555",
                        transition: "all 0.12s",
                      }}>
                      {d.label}
                    </button>
                  );
                })}
              </div>
            </div>

            <div style={field}>
              <label style={label}>Lectureship</label>
              <label style={{ display: "flex", alignItems: "center", gap: 10, cursor: "pointer", padding: "10px 12px", borderRadius: 8, border: "0.5px solid #d0d0d0", background: "#fafafa" }}>
                <input
                  type="checkbox"
                  checked={formData.lectureship}
                  onChange={e => setFormData(p => ({ ...p, lectureship: e.target.checked }))}
                  style={{ width: 16, height: 16, accentColor: "#111", cursor: "pointer" }}
                />
                <span style={{ fontSize: 13, color: "#333" }}>Is a lecturer</span>
              </label>
            </div>

            <div style={divider} />

            {/* Submit */}
            <button type="submit" disabled={loading}
              style={{
                width: "100%", padding: "11px", fontSize: 14, fontWeight: 600,
                borderRadius: 8, border: "none", cursor: loading ? "not-allowed" : "pointer",
                background: loading ? "#e0e0e0" : "#111", color: loading ? "#999" : "#fff",
                transition: "background 0.15s",
              }}>
              {loading ? "Saving…" : "Save profile"}
            </button>

          </form>
        </div>
    </div>
    </div>
  );
}