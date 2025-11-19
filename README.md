# Trabajo Práctico Integrador – Programación 2  
## Tecnicatura Universitaria en Programación a Distancia (TUPaD)  
### Universidad Tecnológica Nacional – UTN  
**Año:** 2025  
**Cátedra:** Programación  

---

## 🧩 Descripción del Proyecto
Este Trabajo Práctico Integrador consiste en el desarrollo de un sistema de gestión de pacientes e historias clínicas utilizando Java, JDBC y arquitectura por capas.

Se aplican:
- POO  
- Excepciones  
- Collections y Genéricos  
- JDBC con MySQL  
- Patrón DAO/Service  
- Validaciones y transacciones  
- Menú interactivo en consola  

---

## 🧱 Arquitectura
- **Config:** conexión BD  
- **DAO:** acceso a datos  
- **Service:** lógica de negocio  
- **Models:** entidades  
- **Main:** menú interactivo  

---

## 👥 Integrantes y responsabilidades
- **Lautaro Pez:** Clases Service  
- **Cristian Paolucci:** Clases DAO  
- **Emmanuel González:** Conexión BD y menú  
- **Facundo Milano:** Integración general del proyecto, validación funcional y documentación técnica  

---

## 🛠 Tecnologías
- Java 17+  
- JDBC (MySQL Connector/J)  
- MySQL  
- NetBeans  

---

## ▶️ Ejecución
1. Agregar mysql-connector-j al Classpath  
2. Configurar `DatabaseConnection.java`  
3. Ejecutar `MainTPint.java`  

---

## 📂 Estructura del Proyecto
```
src/
 ├── Config/
 ├── DAO/
 ├── Models/
 ├── Service/
 └── Main/
```
