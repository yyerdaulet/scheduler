import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import api from "../api";

/* ─── Font + styles ──────────────────────────────────────────────────────── */
const fontLink = document.createElement("link");
fontLink.rel = "stylesheet";
fontLink.href =
  "https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,600;0,700;1,600&family=Lato:wght@300;400;700&display=swap";
if (!document.head.querySelector('link[href*="Playfair"]')) {
  document.head.appendChild(fontLink);
}

const styleTag = document.createElement("style");
styleTag.setAttribute("data-mp", "1");
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

  .mp-page {
    font-family: 'Lato', sans-serif;
    color: var(--ink);
    background: var(--bg);
    min-height: 100vh;
  }

  /* HERO */
  .mp-hero {
    background: linear-gradient(135deg, #fff8dc 0%, #fde68a 55%, #fbbf24 100%);
    padding: 80px 24px 90px;
    position: relative;
    overflow: hidden;
    text-align: center;
    color: var(--ink);
  }

  .mp-hero::before {
    content: '';
    position: absolute; top: -80px; right: -80px;
    width: 380px; height: 380px; border-radius: 50%;
    background: rgba(251,191,36,0.15);
  }

  .mp-hero::after {
    content: '';
    position: absolute; bottom: -100px; left: 10%;
    width: 260px; height: 260px; border-radius: 50%;
    background: rgba(251,191,36,0.08);
  }

  .mp-hero-eyebrow {
    background: var(--accent-pale);
    border: 1px solid var(--border);
    color: var(--muted);
  }

  .mp-hero-desc {
    color: var(--muted);
  }

  .mp-hero-chip {
    background: var(--accent-pale);
    border: 1px solid var(--border);
    color: var(--ink2);
  }

  /* CONTENT */
  .mp-content {
    max-width: 1100px;
    margin: 0 auto;
    padding: 64px 24px 80px;
  }

  .mp-section-title {
    color: var(--ink);
  }

  .mp-section-line {
    background: var(--border);
  }

  /* ABOUT */
  .mp-about-card {
    border: 1.5px solid var(--border);
    box-shadow: 0 2px 16px rgba(251,191,36,0.12);
  }

  .mp-about-card::before {
    background: linear-gradient(to bottom, var(--accent), var(--accent2));
  }

  .mp-about-text {
    color: var(--ink2);
  }

  /* DIRECTIONS */
  .mp-dir-card {
    border: 1.5px solid var(--border);
    box-shadow: 0 2px 12px rgba(251,191,36,0.10);
  }

  .mp-dir-card:hover {
    box-shadow: 0 6px 28px rgba(251,191,36,0.18);
    border-color: var(--accent);
  }

  .mp-dir-card::after {
    color: var(--border);
  }

  .mp-dir-card:hover::after {
    color: var(--accent2);
  }

  .mp-dir-name {
    color: var(--ink);
  }

  /* NEWS */
  .mp-news-card {
    border: 1.5px solid var(--border);
    box-shadow: 0 2px 12px rgba(251,191,36,0.10);
  }

  .mp-news-card:hover {
    box-shadow: 0 6px 24px rgba(251,191,36,0.18);
  }

  .mp-news-date {
    color: var(--accent2);
    background: var(--accent-bg);
  }

  .mp-news-text {
    color: var(--ink2);
  }

  /* EMPTY */
  .mp-empty {
    color: var(--muted);
  }

  /* DIVIDER */
  .mp-divider {
    background: var(--border);
  }

  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(16px); }
    to   { opacity: 1; transform: translateY(0); }
  }

  .mp-animate {
    animation: fadeUp 0.4s ease both;
  }
`;
if (!document.head.querySelector("style[data-mp]")) {
  document.head.appendChild(styleTag);
}

/* ─── Direction config ───────────────────────────────────────────────────── */
const DIRECTIONS = [
  { id: 1, label: "Information Systems",               icon: "🗄️", color: "#eef3ff", iconBg: "#dce8ff" },
  { id: 2, label: "Artificial Intelligence & Big Data", icon: "🤖", color: "#f0fdf4", iconBg: "#d1fae5" },
  { id: 3, label: "Computer Science",                  icon: "💻", color: "#fdf4ff", iconBg: "#ede9fe" },
  { id: 4, label: "Cybersecurity & Cryptology",        icon: "🔐", color: "#fff7ed", iconBg: "#fed7aa" },
];

/* ─── Component ──────────────────────────────────────────────────────────── */
export default function MainPage() {
  const [news, setNews] = useState([]);

  useEffect(() => {
    api.get("/news")
      .then((res) => setNews(res.data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <div className="mp-page">

      {/* ── Hero ── */}
      <div className="mp-hero mp-animate">
        <div className="mp-hero-eyebrow">🔬 Research Laboratory</div>
        <div className="mp-hero-title">IT Research Laboratory</div>
        <div className="mp-hero-desc">
          A research and development environment focused on software engineering,
          artificial intelligence, and distributed systems — exploring emerging
          technologies and building practical digital solutions.
        </div>
        <div className="mp-hero-chips">
          <span className="mp-hero-chip">Machine Learning</span>
          <span className="mp-hero-chip">Cloud Computing</span>
          <span className="mp-hero-chip">Cybersecurity</span>
          <span className="mp-hero-chip">Data Analytics</span>
        </div>
      </div>

      <div className="mp-content">

        {/* ── About ── */}
        <div className="mp-animate" style={{ animationDelay: "0.05s" }}>
          <div className="mp-section-head">
            <div className="mp-section-title">About Us</div>
            <div className="mp-section-line" />
          </div>
          <div className="mp-about-card">
            <p className="mp-about-text">
              The laboratory was established in the mid-2010s by a small group of software engineers
              and researchers interested in exploring emerging digital technologies. Initially, the team
              focused on experimental projects in web development and distributed systems. As the
              laboratory grew, its activities expanded to include research in artificial intelligence,
              data processing, and cloud computing. Over time, the laboratory developed several
              prototype tools and research projects, gradually evolving into a collaborative
              environment for innovation and technological experimentation.
            </p>
          </div>
        </div>

        {/* ── Directions ── */}
        <div className="mp-animate" style={{ animationDelay: "0.10s" }}>
          <div className="mp-section-head">
            <div className="mp-section-title">Research Directions</div>
            <div className="mp-section-line" />
          </div>
          <div className="mp-directions">
            {DIRECTIONS.map((d, i) => (
              <Link
                key={d.id}
                to={`/labs/${d.id}`}
                className="mp-dir-card mp-animate"
                style={{ animationDelay: `${0.12 + i * 0.05}s`, background: d.color, borderColor: "transparent" }}
              >
                <div className="mp-dir-icon" style={{ background: d.iconBg }}>
                  {d.icon}
                </div>
                <div className="mp-dir-name">{d.label}</div>
              </Link>
            ))}
          </div>
        </div>

        {/* ── News ── */}
        <div className="mp-animate" style={{ animationDelay: "0.15s" }}>
          <div className="mp-section-head">
            <div className="mp-section-title">Latest News</div>
            <div className="mp-section-line" />
          </div>

          {news.length === 0 ? (
            <div className="mp-empty">
              <div style={{ fontSize: "2rem", marginBottom: 10 }}>📭</div>
              No news published yet.
            </div>
          ) : (
            <div className="mp-news-grid">
              {news.map((item, i) => (
                <div
                  key={i}
                  className="mp-news-card mp-animate"
                  style={{ animationDelay: `${0.18 + i * 0.04}s` }}
                >
                  <div className="mp-news-date">📅 {item.publication_date}</div>
                  <div className="mp-news-text">{item.publication_text}</div>
                </div>
              ))}
            </div>
          )}
        </div>

      </div>
    </div>
  );
}