import React, { useState } from "react";
import "../styles/TranslatorPage.css";

export default function TranslatorPage() {
  const [hrText, setHrText] = useState("");
  const [jpText, setJpText] = useState("");
  const [selectedScript, setSelectedScript] = useState("hiragana");

  const translateHrToJp = async () => {
    alert("Prevođenje s hrvatskog na japanski je u fazi razvoja. Hvala na strpljenju!");
  };

  const translateJpToHr = async () => {
    alert("Prevođenje s japanskog na hrvatski je u fazi razvoja. Hvala na strpljenju!");
  };

  return (
    <div className="translator-container">
      <h1>Prevoditelj</h1>
      <div className="translator-boxes">
        <div className="translator-box">
          <div className="translator-label">Hrvatski</div>
          <textarea
            value={hrText}
            onChange={e => setHrText(e.target.value)}
            placeholder="Upiši tekst na hrvatskom..."
            rows={8}
          />
          <button onClick={translateHrToJp} disabled={!hrText}>
            Prevedi na japanski
          </button>
        </div>
        
        <div className="translator-box">
          <div className="translator-script-header">
            <div className="translator-label">Japanski</div>
            <select
              value={selectedScript}
              onChange={e => setSelectedScript(e.target.value)}
              className="script-selector"
            >
              <option value="hiragana">Hiragana</option>
              <option value="katakana">Katakana</option>
              <option value="kanji">Kanji</option>
            </select>
          </div>
          <textarea
            value={jpText}
            onChange={e => setJpText(e.target.value)}
            placeholder="Upiši tekst na japanskom..."
            rows={8}
          />
          <button onClick={translateJpToHr} disabled={!jpText}>
            Prevedi na hrvatski
          </button>
        </div>
      </div>
    </div>
  );
}
