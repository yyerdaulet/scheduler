import React, { useState, useEffect } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

const fontLink = document.createElement("link");
fontLink.rel = "stylesheet";
fontLink.href =
  "https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&family=Lato:wght@300;400;700&display=swap";
if (!document.head.querySelector('link[href*="Playfair"]')) {
  document.head.appendChild(fontLink);
}

const styleTag = document.createElement("style");
styleTag.setAttribute("data-pf", "1");
styleTag.textContent = `
  :root {
    --ink:          #1a1500;
    --ink2:         #4a3e00;
    --muted:        #a89c6e;
    --border:       #fde68a;
    --bg:           #ffffff;
    --surface:      #ffffff;
    --accent:       #fbbf24;
    --accent2:      #f59e0b;
    --accent-bg:    #fef3c7;
    --accent-pale:  #fef9e7;
  }

  .pf-page {
    font-family: 'Lato', sans-serif;
    background: var(--bg);
    min-height: 100vh;
    display: flex; align-items: center; justify-content: center;
    padding: 48px 16px 80px;
  }

  .pf-shell { width: 100%; max-width: 520px; }

  .pf-brand { text-align: center; margin-bottom: 28px; }

  .pf-brand-icon {
    width: 52px; height: 52px;
    background: var(--accent-pale);
    border: 2px solid var(--accent);
    border-radius: 14px;
    display: inline-flex; align-items: center; justify-content: center;
    font-size: 1.4rem;
    margin-bottom: 12px;
  }

  .pf-brand-name {
    font-family: 'Playfair Display', serif;
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--ink);
  }

  .pf-brand-sub {
    font-size: 0.78rem;
    color: var(--muted);
  }

  /* HERO */
  .pf-hero {
    background: linear-gradient(135deg, #fff8dc 0%, #fde68a 60%, #fbbf24 100%);
    border-radius: 16px 16px 0 0;
    padding: 28px 36px;
    position: relative;
  }

  .pf-hero::before {
    background: rgba(251,191,36,0.15);
  }

  .pf-hero-title {
    color: var(--ink);
  }

  .pf-hero-sub {
    color: var(--muted);
  }

  .pf-step {
    background: var(--accent-pale);
  }

  .pf-step.done { background: var(--accent2); }
  .pf-step.active { background: var(--accent); }

  /* BODY */
  .pf-body {
    background: var(--surface);
    border: 1.5px solid var(--border);
    border-top: none;
    box-shadow: 0 8px 32px rgba(251,191,36,0.18);
  }

  .pf-section-label {
    color: var(--accent2);
    border-bottom: 2px solid var(--accent-bg);
  }

  .pf-label { color: var(--ink2); }

  .pf-input, .pf-select {
    border: 1.5px solid var(--border);
    background: var(--accent-pale);
  }

  .pf-input:focus, .pf-select:focus {
    border-color: var(--accent);
    box-shadow: 0 0 0 3px rgba(251,191,36,0.2);
    background: #fff;
  }

  .pf-input::placeholder { color: #d4c98a; }

  .pf-degree-btn {
    border: 1.5px solid var(--border);
    background: var(--accent-pale);
    color: var(--muted);
  }

  .pf-degree-btn:hover {
    border-color: var(--accent);
    color: var(--accent2);
  }

  .pf-degree-btn.active {
    border-color: var(--accent);
    background: var(--accent-bg);
    color: var(--accent2);
  }

  .pf-divider { background: var(--border); }

  /* LAB */
  .pf-lab-btn {
    border: 1.5px solid var(--border);
    background: var(--accent-pale);
  }

  .pf-lab-btn:hover {
    border-color: var(--accent);
    background: var(--accent-bg);
  }

  .pf-lab-btn.active {
    border-color: var(--accent);
    background: var(--accent-bg);
    box-shadow: 0 0 0 3px rgba(251,191,36,0.2);
  }

  .pf-lab-radio {
    border: 2px solid var(--border);
  }

  .pf-lab-btn.active .pf-lab-radio {
    border-color: var(--accent);
    background: var(--accent);
  }

  .pf-lab-name { color: var(--ink); }

  .pf-lab-info { color: var(--muted); }

  .pf-lab-none {
    border: 1.5px dashed var(--border);
    background: var(--accent-pale);
  }

  .pf-lab-none:hover {
    border-color: var(--muted);
  }

  .pf-lab-none.active {
    border-color: var(--accent);
    background: var(--accent-bg);
    color: var(--accent2);
  }

  .pf-lab-spinner {
    border: 2px solid var(--border);
    border-top-color: var(--accent);
  }

  /* SUBMIT */
  .pf-submit {
    background: var(--accent-soft);
    color: var(--ink2);
    border: 1.5px solid var(--accent);
    box-shadow: 0 4px 16px rgba(251,191,36,0.25);
  }

  .pf-submit:hover {
    background: var(--accent);
  }

  /* ERROR */
  .pf-error {
    background: #fffbeb;
    border: 1.5px solid var(--accent);
    color: #92400e;
  }

  @keyframes pf-spin {
    to { transform: rotate(360deg); }
  }

  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(16px); }
    to   { opacity: 1; transform: translateY(0); }
  }

  .pf-animate {
    animation: fadeUp 0.38s ease both;
  }
`;
if (!document.head.querySelector("style[data-pf]")) {
  document.head.appendChild(styleTag);
}

const DEGREES = [
  { value: "BACHELOR", label: "Bachelor", icon: "📘" },
  { value: "MASTER",   label: "Master",   icon: "📗" },
  { value: "DOCTOR",   label: "Doctor",   icon: "📙" },
];

const ProfileForm = () => {
  const navigate = useNavigate();
  const id = localStorage.getItem("id");

  const [formData, setFormData] = useState({
    user_id: id,
    orcid: "",
    name: "",
    lastName: "",
    birthday: "",
    degree: "BACHELOR",
    lab_id: 1,
  });
  const [labs, setLabs] = useState([]);
  const [labsLoading, setLabsLoading] = useState(true);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/labs")
      .then((r) => {
          console.log(r.data);
          setLabs(Array.isArray(r.data) ? r.data : [])})
      .catch(console.error)
      .finally(() => setLabsLoading(false));


  }, []);

  const handleChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      await api.post("/profiles", formData);
      navigate(`/profiles/${id}`);
    } catch (err) {
      console.error(err);
      setError("Failed to save profile. Please check your details and try again.");
      setLoading(false);
    }
  };

  // progress: name, lastName, birthday, degree, lab (5 steps)
  const filled = [
    formData.name,
    formData.lastName,
    formData.birthday,
    formData.degree,
    formData.lab_id,
  ].filter(Boolean).length;

  return (
    <div className="pf-page">
      <div className="pf-shell pf-animate">

        {/* Brand */}
        <div className="pf-brand">
          <div className="pf-brand-icon">🎓</div>
          <div className="pf-brand-name">ResearchHub</div>
          <div className="pf-brand-sub">Academic Profile Platform</div>
        </div>

        {/* Hero */}
        <div className="pf-hero">
          <div className="pf-hero-title">Set Up Your Profile</div>
          <div className="pf-hero-sub">Complete your researcher identity</div>
          <div className="pf-steps">
            {[0, 1, 2, 3, 4].map((i) => (
              <div
                key={i}
                className={`pf-step ${i < filled ? "done" : i === filled ? "active" : ""}`}
              />
            ))}
          </div>
        </div>

        {/* Body */}
        <div className="pf-body">
          {error && <div className="pf-error">⚠️ {error}</div>}

          <form onSubmit={handleSubmit}>

            {/* Personal info */}
            <div className="pf-section-label">👤 Personal Information</div>

            <div className="pf-grid-2">
              <div className="pf-field">
                <label className="pf-label">First Name <sup>*</sup></label>
                <input
                  className="pf-input" type="text" name="name"
                  value={formData.name} onChange={handleChange}
                  placeholder="Jane" required
                />
              </div>
              <div className="pf-field">
                <label className="pf-label">Last Name <sup>*</sup></label>
                <input
                  className="pf-input" type="text" name="lastName"
                  value={formData.lastName} onChange={handleChange}
                  placeholder="Doe" required
                />
              </div>
            </div>

            <div className="pf-field">
              <label className="pf-label">Date of Birth <sup>*</sup></label>
              <input
                className="pf-input" type="date" name="birthday"
                value={formData.birthday} onChange={handleChange} required
              />
            </div>

            <div className="pf-divider" />

            {/* Academic info */}
            <div className="pf-section-label">🔬 Academic Details</div>

            <div className="pf-field">
              <label className="pf-label">ORCID iD</label>
              <input
                className="pf-input" type="text" name="orcid"
                value={formData.orcid} onChange={handleChange}
                placeholder="0000-0000-0000-0000"
              />
              <div className="pf-hint">
                Your unique researcher identifier — find yours at{" "}
                <a href="https://orcid.org" target="_blank" rel="noreferrer"
                  style={{ color: "var(--accent)", fontWeight: 700, textDecoration: "none" }}>
                  orcid.org
                </a>
              </div>
            </div>

            <div className="pf-field">
              <label className="pf-label">Academic Degree <sup>*</sup></label>
              <div className="pf-degree-group">
                {DEGREES.map((d) => (
                  <button
                    key={d.value} type="button"
                    className={`pf-degree-btn ${formData.degree === d.value ? "active" : ""}`}
                    onClick={() => setFormData({ ...formData, degree: d.value })}
                  >
                    <span className="pf-degree-icon">{d.icon}</span>
                    {d.label}
                  </button>
                ))}
              </div>
            </div>

            <div className="pf-divider" />

            {/* Lab selection */}
            <div className="pf-section-label">🏛 Research Laboratory</div>

            <div className="pf-field">
              <label className="pf-label">Choose your lab</label>

              {labsLoading ? (
                <div className="pf-lab-loading">
                  <div className="pf-lab-spinner" />
                  Loading laboratories…
                </div>
              ) : (
                <div className="pf-lab-grid">
                  {labs.map((lab) => {
                    const isActive = formData.lab_id === lab.id;
                    return (
                      <button
                        key={lab.id}
                        type="button"
                        className={`pf-lab-btn ${isActive ? "active" : ""}`}
                        onClick={() => setFormData({ ...formData, lab_id: isActive ? null : lab.id })}
                      >
                        <div className="pf-lab-radio">
                          {isActive && <div className="pf-lab-radio-dot" />}
                        </div>
                        <div>
                          <div className="pf-lab-name">{lab.name}</div>
                          {lab.info && <div className="pf-lab-info">{lab.info}</div>}
                        </div>
                      </button>
                    );
                  })}

                  {/* None / skip option */}

                </div>
              )}


            </div>

            <div className="pf-divider" />

            <button type="submit" className="pf-submit" disabled={loading}>
              {loading ? "⏳ Saving…" : "Save Profile →"}
            </button>

          </form>
        </div>
      </div>
    </div>
  );
};

export default ProfileForm;
