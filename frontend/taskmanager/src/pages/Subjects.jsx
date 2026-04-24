import { useState } from "react";
import api from "../api";

export default function CreateSubject() {
  const [formSubject, setFormSubject] = useState({
    name: "",
    credits: 5
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) =>
    setFormSubject({ ...formSubject, [e.target.name]: e.target.value });

  const handleSubmit = async () => {
    setLoading(true);
    setError(null);
    try {
      await api.post("/subjects", formSubject);
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
        <h1 style={{ fontSize: 22, fontWeight: 500, margin: 0 }}>Create Subject</h1>
      </div>

      <div style={{ display: "flex", flexDirection: "column", gap: 20 }}>
        <div>
          <label style={labelStyle}>Subject name</label>
          <input
            name="name"
            value={formSubject.name}
            onChange={handleChange}
            placeholder="e.g. CS-102"
            style={inputStyle}
          />
        </div>

        <div>
          <label style={labelStyle}>Credits amount</label>
          <input
            type="number"
            name="credits"
            value={formSubject.credits}
            onChange={handleChange}
            style={inputStyle}
          />
        </div>



        {error && <p style={{ color: "#c0392b", fontSize: 13, margin: 0 }}>{error}</p>}

        <div style={{ borderTop: "0.5px solid #e0e0e0", paddingTop: 20, display: "flex", justifyContent: "flex-end" }}>
          <button
            onClick={handleSubmit}
            disabled={loading || !formSubject.name.trim()}
            style={{
              padding: "10px 24px",
              fontSize: 14,
              fontWeight: 500,
              borderRadius: 8,
              border: "none",
              background: formSubject.name.trim() ? "#1a1a1a" : "#ccc",
              color: "#fff",
              cursor: formSubject.name.trim() ? "pointer" : "not-allowed",
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