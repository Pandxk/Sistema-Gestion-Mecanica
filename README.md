# 🛠️ Sistema de Gestión para Taller Mecánico

Aplicación de escritorio desarrollada en **Java (Swing / NetBeans)** con persistencia **JPA** sobre **MySQL** y generación de reportes en PDF mediante **JasperReports** para la administración integral de clientes, vehículos, productos y órdenes de servicio.

---

## ✨ Funcionalidades Principales

- 🔐 **Autenticación de usuarios** (`Login.java`, `Usuario.java`) y panel principal (`DashBoard.java`, `Sistema.java`).
- 👥 **Gestión de Clientes y Vehículos** (`ClienteGUI.java`, `FormularioCliente.java`, `VehiculoGUI.java`, `FormularioVehiculo.java`).
- 📦 **Control de Productos e Inventario** (`ProductoGUI.java`, `FormularioProductos.java`).
- 🔧 **Órdenes de Servicio y Resumen** (`ServicioGUI.java`, `ResumenOrdenGUI.java`).
- 📊 **Reportes en PDF (JasperReports)** en `src/Reportes/`:
  - `Mejores_Clientes`
  - `ReporteHistorial`
  - `ReporteIngresos`
  - `ReporteStockCritico`
  - `Reporte_mecanica`
- 🗄️ **Base de datos incluida**: script completo `BDMECANICA.sql` (esquema `bdmecanica` con tablas `cliente`, `vehiculo`, `producto`, `servicio`, `detalle_servicio` y `usuario`).

---

## 🏗️ Estructura del Proyecto

```text
Sistema-Gestion-Mecanica/
├── BDMECANICA.sql                # Dump MySQL (esquema bdmecanica)
├── build.xml                     # Configuración de compilación Ant (NetBeans)
├── nbproject/                    # Metadatos del proyecto NetBeans
└── src/
    ├── Controladores/            # Controladores JPA (*JpaController.java)
    ├── Entidades/                # Entidades JPA (Cliente, Vehiculo, Producto, Servicio, Usuario)
    ├── Forms/                    # Interfaces gráficas Swing (.java y .form)
    ├── Images/                   # Iconos y recursos visuales
    ├── META-INF/                 # persistence.xml
    ├── Reportes/                 # Plantillas .jrxml y compilados .jasper
    └── miproyectomecanica/       # Clase principal MiProyectoMecanica.java
```

---

## 🚀 Requisitos y Puesta en Marcha

1. **Java JDK** (8 o superior) y **Apache NetBeans** (recomendado para abrir el proyecto Ant).
2. **MySQL Server** (8.x).
3. Importa la base de datos:
   ```sql
   CREATE DATABASE IF NOT EXISTS bdmecanica;
   USE bdmecanica;
   SOURCE BDMECANICA.sql;
   ```
4. Verifica las credenciales de conexión en `src/META-INF/persistence.xml`.
5. Abre el proyecto en **NetBeans** y ejecútalo desde `MiProyectoMecanica.java`.

---

## 👤 Autor

- **Jose Maldonado** ([Pandxk](https://github.com/Pandxk))

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**. Consulta el archivo [LICENSE](./LICENSE) para más detalles.
