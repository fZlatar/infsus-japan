import os
import uuid
import binascii
import pandas as pd

def read_sql_file(filepath):
   with open(filepath, "r", encoding="utf-8") as f:
      return f.read()

def encode_file_to_hex(file_path):
   with open(file_path, "rb") as f:
      binary_data = f.read()
   # Convert binary to hex string prefixed with '\x' for PostgreSQL bytea input
   hex_data = "\\x" + binascii.hexlify(binary_data).decode('ascii')
   return hex_data

def generate_file_inserts(folder_path, media_type):
   inserts = []
   file_uuid_map = {}  # map filename to uuid to reference later

   for filename in os.listdir(folder_path):
      if filename.startswith('.'):
         continue  # skip hidden files, if any 

      file_path = os.path.join(folder_path, filename)
      if not os.path.isfile(file_path):
         continue

      file_uuid = str(uuid.uuid4())
      base_name = os.path.splitext(filename)[0].strip()
      file_uuid_map[base_name] = file_uuid

      hex_data = encode_file_to_hex(file_path)

      insert = f"('{file_uuid}', decode('{hex_data[2:]}', 'hex'), '{media_type}', '{filename}')"
      inserts.append(insert)

   return inserts, file_uuid_map

def generate_term_inserts(excel_path, audio_uuid_map, image_uuid_map):
   insert_header = "INSERT INTO term (difficulty, term_name_cro, term_name_jpn, description_cro, description_jpn, audio_UUID, image_UUID) VALUES "

   df = pd.read_excel(excel_path)
   inserts = []

   for _, row in df.iterrows():
      difficulty = int(row['difficulty'])
      term_name_cro = row['term_name_cro'].replace("'", "''")
      term_name_jpn = row['term_name_jpn'].replace("'", "''")
      description_cro = row['description_cro'].replace("'", "''")
      description_jpn = row['description_jpn'].replace("'", "''")
      id = str(row.get("id"))

      audio_uuid = audio_uuid_map.get(id)
      image_uuid = image_uuid_map.get(id)

      # Format UUID values for SQL (NULL or quoted string)
      audio_uuid_sql = f"'{audio_uuid}'" if audio_uuid is not None else 'NULL'
      image_uuid_sql = f"'{image_uuid}'" if image_uuid is not None else 'NULL'

      insert = f"({difficulty}, '{term_name_cro}', '{term_name_jpn}', '{description_cro}', '{description_jpn}', {audio_uuid_sql}, {image_uuid_sql})"
      inserts.append(insert)

   gen_sql = f"{insert_header}\n{",\n".join(inserts)};"
   return gen_sql

def main():
   # Paths
   init1_sql_path = "init1.sql"
   init2_sql_path = "init2.sql"
   excel_path = "term.xlsx"
   audio_folder = "audio"
   image_folder = "image"

   # Read init SQL files
   init1_sql = read_sql_file(init1_sql_path)
   init2_sql = read_sql_file(init2_sql_path)

   insert_file_header = "INSERT INTO file (UUID, data, media_type, file_name) VALUES"

   # Generate file inserts and UUID maps
   audio_inserts, audio_uuid_map = generate_file_inserts(audio_folder, "audio/mpeg")
   image_inserts, image_uuid_map = generate_file_inserts(image_folder, "image/png")

   combined_inserts = audio_inserts + image_inserts

   file_sql = f"{insert_file_header}\n{",\n".join(combined_inserts)};"

   # Generate term inserts with UUID references
   term_sql = generate_term_inserts(excel_path, audio_uuid_map, image_uuid_map)

   full_sql = f"{init1_sql}\n\n{file_sql}\n\n{term_sql}\n\n{init2_sql}"

   # Write combined SQL to file
   with open("init.sql", "w", encoding="utf-8") as f:
      f.write(full_sql)

   print("Combined SQL script written to init.sql")

if __name__ == "__main__":
   main()
