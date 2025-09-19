import re
import os
import json

def parse_boleta(content):
    def find(pattern, default=None):
        match = re.search(pattern, content)
        return match.group(1).strip() if match else default

    # Campos fijos
    data = {
        "Entidad": find(r"\*CL\s+([^\n]+)"),
        "RUC": find(r"RUC\s*-\s*(\d{11})"),
        "Codigo_Modular": find(r"RUC\s*-\s*\d{11}\s+(\d+)-\d+"),
        "Fecha_Boleta": find(r"([A-ZÑ]+\s*-\s*\d{4})"),
        "Tipo": find(r"([A-Z/]+)\s+\("), 
        "Apellidos": find(r'Apellidos\s+:\s+([^\n]+)'),
        "Nombres": find(r'Nombres\s+:\s+([^\n]+)'),
        "Fecha_Nacimiento": find(r'Fecha de Nacimiento\s+:\s+([^\n]+)'),
        "Documento": find(r'Documento de Identidad\s+\S+\s+([^\n]+)'),
        "Cargo": find(r'Cargo\s+:\s+([^\n]+)'),
        "Tipo_Pensionista": find(r'Tipo de Pensionista\s+:\s+([^\n]+)'),
        "Tipo_Pension": find(r'Tipo de Pension\s+:\s+([^\n]+)'),
        "NivMag_Grupo_Horas": find(r'Niv\.Mag\./Grupo Ocup\./Horas\s+:\s+([^\n]+)'),
        "Tiempo_Servicio": find(r'Tiempo de Servicio \(AA-MM-DD\):\s*([^\s]+)'),
        "ESSALUD": find(r'ESSALUD\s*:\s*([^\s]*)'),
        "Fecha_Cese": find(r'Cese\s*:\s*Termino:([^\n]+)'),
        "Cuenta": find(r'Cta\. TeleAhorro o Nro\. Cheque:\s*([^\n]+)'),
        "Leyenda_Permanente": find(r'Leyenda Permanente\s*:\s*([^\n]*)'),
        "Leyenda_Mensual": find(r'Leyenda Mensual\s*:\s*([^\n]*)'),
    }

    # Haberes y descuentos variables
    data['Haberes'] = [
        {"nombre": k, "valor": float(v)}
        for k, v in re.findall(r'\+([a-zA-Z0-9]+)\s+([0-9.]+)', content)
    ]
    data['Descuentos'] = [
        {"nombre": k, "valor": float(v)}
        for k, v in re.findall(r'-([a-zA-Z0-9]+)\s+([0-9.]+)', content)
    ]

    # Totales (maneja None si no hay valor)
    def find_float(pattern):
        val = find(pattern)
        try:
            return float(val) if val else None
        except:
            return None
    data['Totales'] = {
        "T_REMUN": find_float(r'T-REMUN\s+([0-9.]+)'),
        "T_DSCTO": find_float(r'T-DSCTO\s+([0-9.]+)'),
        "T_LIQUI": find_float(r'T-LIQUI\s+([0-9.]+)'),
        "MImponible": find_float(r'MImponible\s+([0-9.]+)')
    }
    data['Mensaje'] = find(r'Mensajes\s*:\s*([^\n]*)')

    return data

def parse_boletas_from_txt(txt_path):
    # Prueba utf-8, luego latin-1, luego utf-8-sig
    for encoding in ["utf-8", "latin-1", "utf-8-sig"]:
        try:
            with open(txt_path, "r", encoding=encoding) as f:
                text = f.read()
            break
        except UnicodeDecodeError:
            continue
    else:
        print(f"Error: No se pudo leer {txt_path} con ningún encoding.")
        return []
    partes = text.split('========================================================================')
    boletas = []
    for parte in partes[1:]:
        if "Apellidos" in parte:
            boletas.append(parse_boleta(parte))
    return boletas

def main():
    folder = "."  # Cambia a tu carpeta real, o usa "." si están en la misma carpeta
    all_boletas = []
    for file in os.listdir(folder):
        if file.endswith('.txt'):
            boletas = parse_boletas_from_txt(os.path.join(folder, file))
            all_boletas.extend(boletas)
    # Guardar todas las boletas en un solo archivo JSON
    with open("boletas_limpias.json", "w", encoding="utf-8") as fout:
        json.dump(all_boletas, fout, ensure_ascii=False, indent=2)
    print(f"Guardadas {len(all_boletas)} boletas en boletas_limpias.json")

if __name__ == "__main__":
    main()