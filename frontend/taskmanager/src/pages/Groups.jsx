import { useState } from "react";
import api from "../api";

const DIRECTIONS = [
  { value: "PROGRAMMING", label: "Programming" },
  { value: "BIG_DATA", label: "Big Data" },
  { value: "GENERAL", label: "General" },
];

export default function CreateGroup() {
  const [formGroup, setFormGroup] = useState({
    name: "",
    enrolledYear: 2026,
    direction: "BIG_DATA",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) =>
    setFormGroup({ ...formGroup, [e.target.name]: e.target.value });

  const handleSubmit = async () => {
    setLoading(true);
    setError(null);
    try {
      await api.post("/groups", formGroup);
    } catch (err) {
      setError("Failed to create group. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 480, margin: "2rem auto", padding: "0 1rem" }}>
      <div style={{ marginBottom: "2rem" }}>
        <p style={{ fontSize: 12, letterSpacing: "0.08em", color: "#888", textTransform: "uppercase", margin: "0 0 6px" }}>
          Administration
        </p>
        <h1 style={{ fontSize: 22, fontWeight: 500, margin: 0 }}>Create group</h1>
      </div>

      <div style={{ display: "flex", flexDirection: "column", gap: 20 }}>
        <div>
          <label style={labelStyle}>Group name</label>
          <input
            name="name"
            value={formGroup.name}
            onChange={handleChange}
            placeholder="e.g. CS-102"
            style={inputStyle}
          />
        </div>

        <div>
          <label style={labelStyle}>Enrolled year</label>
          <input
            type="number"
            name="enrolledYear"
            value={formGroup.enrolledYear}
            onChange={handleChange}
            style={inputStyle}
          />
        </div>

        <div>
          <label style={labelStyle}>Direction</label>
          <div style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)", gap: 8 }}>
            {DIRECTIONS.map((d) => {
              const active = formGroup.direction === d.value;
              return (
                <button
                  key={d.value}
                  type="button"
                  onClick={() => setFormGroup({ ...formGroup, direction: d.value })}
                  style={{
                    padding: "10px 8px",
                    fontSize: 13,
                    fontWeight: active ? 500 : 400,
                    borderRadius: 8,
                    border: active ? "2px solid #1a6fb5" : "0.5px solid #ccc",
                    background: active ? "#e8f0fa" : "#fff",
                    color: active ? "#1a5fa0" : "#333",
                    cursor: "pointer",
                    transition: "all 0.15s",
                  }}
                >
                  {d.label}
                </button>
              );
            })}
          </div>
        </div>

        {error && <p style={{ color: "#c0392b", fontSize: 13, margin: 0 }}>{error}</p>}

        <div style={{ borderTop: "0.5px solid #e0e0e0", paddingTop: 20, display: "flex", justifyContent: "flex-end" }}>
          <button
            onClick={handleSubmit}
            disabled={loading || !formGroup.name.trim()}
            style={{
              padding: "10px 24px",
              fontSize: 14,
              fontWeight: 500,
              borderRadius: 8,
              border: "none",
              background: formGroup.name.trim() ? "#1a1a1a" : "#ccc",
              color: "#fff",
              cursor: formGroup.name.trim() ? "pointer" : "not-allowed",
            }}
          >
            {loading ? "Creating..." : "Create group"}
          </button>
        </div>
      </div>
    </div>
  );
}

const labelStyle = { display: "block", fontSize: 13, fontWeight: 500, color: "#666", marginBottom: 6 };
const inputStyle = { width: "100%", boxSizing: "border-box", padding: "9px 12px", fontSize: 14, borderRadius: 8, border: "0.5px solid #ccc" };