import React from "react";
import "../styles/MainPage.css";
import progressImg from "../assets/mainPage/img1.jpeg";
import translateImg from "../assets/mainPage/img2.jpeg";
import recommendImg from "../assets/mainPage/img3.jpeg";
import pointsImg from "../assets/mainPage/img4.jpeg";

const features = [
  {
    title: "Praćenje napretka",
    description: "Prati svoj napredak kroz lekcije i kvizove, vidi što si savladao i gdje još trebaš vježbati.",
    img: progressImg
  },
  {
    title: "Prevoditelj s OpenAI API-jem",
    description: "Prevedi japanske riječi i rečenice koristeći naprednu umjetnu inteligenciju.",
    img: translateImg
  },
  {
    title: "Pametne preporuke lekcija",
    description: "Naš sustav preporučuje koje lekcije bi ti najviše koristile na temelju tvojih rezultata.",
    img: recommendImg
  },
  {
    title: "Sustav bodovanja",
    description: "Osvoji bodove rješavanjem zadataka i usporedi svoj napredak s drugima.",
    img: pointsImg
  }
];

export default function MainPage() {
  return (
    <div>
      <section className="hero-section">
        <h1>
          Učenje japanskog jezika <span className="highlight">made easy</span>
        </h1>
        <p className="hero-desc">
          Posebno prilagođeno govornicima hrvatskog jezika.
        </p>
      </section>

      <section className="features-section">
        {features.map((feat, idx) => (
          <div className="flip-card feature-card" key={idx}>
            <div className="flip-card-inner">
              <div className="flip-card-front">
                <img className="feature-img" src={feat.img} alt={feat.title} />
              </div>
              <div className="flip-card-back">
                <h3 className="feature-title">{feat.title}</h3>
                <p className="feature-desc">{feat.description}</p>
              </div>
            </div>
          </div>
        ))}
      </section>
    </div>
  );
}
