from gtts import gTTS
import os
import pandas as pd

def text_to_mp3(text: str, filename: str = "output.mp3", lang: str = "ja"):
   if not text:
      raise ValueError("Text cannot be empty.")

   tts = gTTS(text=text, lang=lang)
   tts.save(filename)
   print(f"MP3 file saved as '{filename}'")

def process_excel(file_path: str, lang: str = "ja"):
   audio_dir = "audio"
   os.makedirs(audio_dir, exist_ok=True)

   df = pd.read_excel(file_path)

   for _, row in df.iterrows():
      id = row["id"]
      term_name = str(row['term_name_jpn']).strip()
      term_description = str(row["description_jpn"]).strip()
      filename = os.path.join(audio_dir, f"{id}.mp3")

      if term_name and term_description and filename:
         combined_text = f"{term_name}。{term_description}"
         try:
            text_to_mp3(combined_text, filename, lang)
         except Exception as e:
            print(f"Error on row {id}: {e}")
      else:
         print(f"Skipping row {id} (missing name or description or filename)")

if __name__ == "__main__":
   process_excel(file_path="./term.xlsx")
