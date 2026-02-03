-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: bdmecanica
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `cedula` varchar(45) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'0102030401','Juan Perez','Av. Las Americas 123','0991234567'),(2,'0102030402','Maria Lopez','Calle Larga 4-56','0992345678'),(3,'0102030403','Pedro Gonzales','Av. Solano 7-89','0993456789'),(4,'0102030404','Ana Torres','Totoracocha','0994567890'),(5,'0102030405','Luis Andrade','Ricaurte','0995678901'),(6,'0102030406','Carmen Velez','San Joaquin','0996789012'),(8,'0102030408','Sofia Mendez','Misicata','0998901234'),(9,'0102030409','Carlos Ruiz','Baños','0999012345'),(10,'0102030410','Elena Castro','Monay','0981234567'),(11,'0102030411','Miguel Silva','Sayausi','0982345678'),(12,'0102030412','Lucia Paredes','Centro Histórico','0983456789'),(13,'0102030413','David Leon','Av. Loja','0984567890'),(14,'0102030414','Paula Ortiz','Don Bosco','0985678901'),(15,'0102030415','Jorge Molina','Miraflores','0986789012'),(16,'0102030416','Andrea Ibarra','El Vergel','0987890123'),(17,'0102030417','Fernando Vega','Challuabamba','0988901234'),(18,'0102030418','Claudia Romero','Av. 12 de Abril','0989012345'),(19,'0102030419','Ricardo Diaz','Av. Remigio Crespo','0971234567'),(20,'0102030420','Valeria Tapia','Parque Industrial','0972345678'),(23,'0706094256','Andres','Americas','0997586514');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_servicio`
--

DROP TABLE IF EXISTS `detalle_servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_servicio` (
  `idDetalle` int NOT NULL AUTO_INCREMENT,
  `id_servicio` int NOT NULL,
  `id_producto` int NOT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` double NOT NULL,
  `subtotal` double NOT NULL,
  PRIMARY KEY (`idDetalle`),
  KEY `fk_detalle_servicio_idx` (`id_servicio`),
  KEY `fk_detalle_producto_idx` (`id_producto`),
  CONSTRAINT `fk_detalle_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `fk_detalle_servicio` FOREIGN KEY (`id_servicio`) REFERENCES `servicio` (`idServicio`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_servicio`
--

LOCK TABLES `detalle_servicio` WRITE;
/*!40000 ALTER TABLE `detalle_servicio` DISABLE KEYS */;
INSERT INTO `detalle_servicio` VALUES (1,24,1,3,15,45),(2,25,1,3,15,45),(3,27,3,2,18.75,37.5),(4,28,3,6,18.75,112.5),(5,29,3,4,18.75,75),(6,30,3,2,18.75,37.5),(7,31,2,2,25,50),(8,32,2,15,25,375),(9,33,1,3,5.5,16.5),(10,34,3,1,18.75,18.75),(11,35,2,1,25,25),(12,36,2,2,25,50),(13,37,14,2,35,70),(14,38,3,1,18.75,18.75),(15,39,8,2,85.5,171),(16,39,12,3,8.5,25.5),(17,40,16,2,250,500),(18,41,10,2,110,220),(19,42,7,2,45,90);
/*!40000 ALTER TABLE `detalle_servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `marca` varchar(45) NOT NULL,
  `stock` int NOT NULL,
  `precio` double NOT NULL,
  PRIMARY KEY (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'FIL-001','Filtro de Aceite','Bosch',41,5.5),(2,'ACE-103','Aceite 10W30 (Galon)','Mobil 1',50,25),(3,'BUJ-777','Juego de bujias','Ngk',49,18.75),(7,'FRN-001','Pastillas de Freno Delanteras','Bosch',38,45),(8,'SUS-002','Amortiguador Trasero','KYB',18,85.5),(9,'LIQ-003','Refrigerante Verde (Galon)','CoolantMax',100,12),(10,'BAT-004','Bateria 12V 70Ah','Bosch',13,110),(11,'EMB-005','Kit de Embrague Completo','Valeo',8,180),(12,'LIM-006','Plumas Limpiaparabrisas','Hella',57,8.5),(13,'FIL-007','Filtro de Combustible','Mann',35,15),(14,'COR-008','Correa de Distribucion','Gates',23,35),(15,'ACE-009','Aceite Sintetico 5W30','Mobil 1',45,32),(16,'ELE-010','Alternador','Denso',3,250);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicio`
--

DROP TABLE IF EXISTS `servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicio` (
  `idServicio` int NOT NULL AUTO_INCREMENT,
  `mantenimiento` varchar(45) DEFAULT NULL,
  `kilometraje` varchar(45) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `Vehiculo_idVehiculo` int NOT NULL,
  `costo` double DEFAULT '0',
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idServicio`),
  KEY `fk_Servicio_Vehiculo1_idx` (`Vehiculo_idVehiculo`),
  CONSTRAINT `fk_Servicio_Vehiculo1_idx` FOREIGN KEY (`Vehiculo_idVehiculo`) REFERENCES `vehiculo` (`idVehiculo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicio`
--

LOCK TABLES `servicio` WRITE;
/*!40000 ALTER TABLE `servicio` DISABLE KEYS */;
INSERT INTO `servicio` VALUES (2,'Frenos','85000','Correctivo','S',2,85,'2026-01-13 00:38:33'),(3,'Alineacion','45000','Preventivo','J',3,30,'2026-01-13 00:38:33'),(4,'Aceite de transmision','98000','Preventivo','I',4,60,'2026-01-13 00:38:33'),(5,'Filtro de Aire','60000','Preventivo','R',5,15,'2026-01-13 00:38:33'),(6,'Bateria','30000','Correctivo','S',6,120,'2026-01-13 00:38:33'),(8,'Llantas','150000','Correctivo','S',8,350,'2026-01-13 00:38:33'),(9,'Luces','20000','Preventivo','J',9,25,'2026-01-13 00:38:33'),(10,'Refrigerante','180000','Preventivo','R',10,20,'2026-01-13 00:38:33'),(11,'Aceite del Motor','55000','Preventivo','I',11,45.5,'2026-01-13 00:38:33'),(12,'Frenos ABC','70000','Preventivo','J',12,50,'2026-01-13 00:38:33'),(13,'Bujias','40000','Preventivo','R',13,40,'2026-01-13 00:38:33'),(15,'Limpieza Inyectores','25000','Preventivo','I',15,35,'2026-01-13 00:38:33'),(16,'Aceite de Caja','105000','Preventivo','R',16,70,'2026-01-13 00:38:33'),(17,'Amortiguadores','190000','Correctivo','S',17,220,'2026-01-13 00:38:33'),(18,'Revision General','10000','Preventivo','J',18,0,'2026-01-13 00:38:33'),(19,'Cambio de Filtros','15000','Preventivo','R',19,25,'2026-01-13 00:38:33'),(20,'Escaneo','95000','Correctivo','I',20,15,'2026-01-13 00:38:33'),(22,'General','85000','Mantenimiento Taller','Terminado',2,0,'2026-01-13 00:38:33'),(23,'General','30000','Mantenimiento Taller','Terminado',6,0,'2026-01-13 00:38:33'),(24,'gfdgdfgfdg','85000','Mantenimiento Taller','Terminado',2,100,'2026-01-13 00:38:33'),(25,'shfjkshfjkdshjfksd','85000','Mantenimiento Taller','Terminado',2,90,'2026-01-13 00:38:33'),(26,'General','','Mantenimiento Taller','Terminado',2,0,'2026-01-13 00:38:33'),(27,'General','45000','Mantenimiento Taller','Terminado',3,37.5,'2026-01-13 00:38:33'),(28,'dhddhdfh','45000','Mantenimiento Taller','Terminado',3,192.5,'2026-01-13 00:38:33'),(29,'dgdfgdf','45000','Mantenimiento Taller','Terminado',3,125,'2026-01-13 00:38:33'),(30,'gjgjhgj','45000','Mantenimiento Taller','Terminado',3,82.5,'2026-01-13 00:38:33'),(31,'General','30000','Mantenimiento Taller','Terminado',6,115,'2026-01-13 00:38:33'),(32,'General','98000','Mantenimiento Taller','Terminado',4,420,'2026-01-13 00:38:33'),(33,'General','','Mantenimiento Taller','Terminado',4,16.5,'2026-01-13 00:38:33'),(34,'kjhkjhk','45000','Orden Generada en Taller','Terminado',3,63.75,'2026-01-13 00:38:33'),(35,'hggjgh','85000','Orden Taller','Terminado',2,70,'2026-01-13 00:38:33'),(36,'dfhgdfhf','120000','Orden Taller','Terminado',1,95,'2026-01-13 14:19:23'),(37,'vffdgdfgdfgd','120000','Orden Taller','Terminado',1,115,'2026-01-13 14:22:21'),(38,'ohhhhhhhhhh','180000','Orden Taller','Terminado',10,68.75,'2026-01-13 14:23:00'),(39,'gjh','98000','Orden Taller','Terminado',4,241.5,'2026-01-13 15:56:42'),(40,'cAMBIO DE ALTERNDOR','60000','Orden Taller','Terminado',5,545,'2026-01-14 20:36:44'),(41,'vhjhjg','30000','Orden Taller','Terminado',6,265,'2026-01-20 09:34:47'),(42,'ghjgjh','60000','Orden Taller','Terminado',5,126.5,'2026-01-20 15:14:18');
/*!40000 ALTER TABLE `servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `pass` varchar(45) DEFAULT NULL,
  `rol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Steve','steve@gmail.com','admin','admin'),(2,NULL,'steve@gmail.com','admin',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculo`
--

DROP TABLE IF EXISTS `vehiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehiculo` (
  `idVehiculo` int NOT NULL AUTO_INCREMENT,
  `placa` varchar(45) DEFAULT NULL,
  `modelo` varchar(45) DEFAULT NULL,
  `marca` varchar(45) DEFAULT NULL,
  `numeroChasis` varchar(45) DEFAULT NULL,
  `numeroMotor` varchar(45) DEFAULT NULL,
  `vin` varchar(45) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `anio` varchar(45) DEFAULT NULL,
  `cilindrada` varchar(45) DEFAULT NULL,
  `combustible` varchar(45) DEFAULT NULL,
  `kilometraje` varchar(45) DEFAULT NULL,
  `Cliente_idCliente` int NOT NULL,
  PRIMARY KEY (`idVehiculo`),
  KEY `fk_Vehiculo_Cliente_idx` (`Cliente_idCliente`),
  CONSTRAINT `fk_Vehiculo_Cliente1` FOREIGN KEY (`Cliente_idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculo`
--

LOCK TABLES `vehiculo` WRITE;
/*!40000 ALTER TABLE `vehiculo` DISABLE KEYS */;
INSERT INTO `vehiculo` VALUES (1,'AAA-1001','Aveo','Chevrolet','CH001','M001','VIN001','Rojo','2015','1.6','Gasolina','120000',1),(2,'BBB-2002','Yaris','Toyota','CH002','M002','VIN002','Azul','2018','1.5','Gasolina','85000',2),(3,'CCC-3003','Rio','Kia','CH003','M003','VIN003','Negro','2020','1.4','Gasolina','45000',3),(4,'DDD-4004','Accent','Hyundai','CH004','M004','VIN004','Blanco','2016','1.6','Gasolina','98000',4),(5,'EEE-5005','Vitara','Chevrolet','CH005','M005','VIN005','Gris','2019','2.0','Gasolina','60000',5),(6,'FFF-6006','Tucson','Hyundai','CH006','M006','VIN006','Plata','2021','2.0','Diesel','30000',6),(8,'HHH-8008','Hilux','Toyota','CH008','M008','VIN008','Blanco','2014','2.7','Diesel','150000',8),(9,'III-9009','D-Max','Chevrolet','CH009','M009','VIN009','Negro','2022','3.0','Diesel','20000',9),(10,'JJJ-1010','Picanto','Kia','CH010','M010','VIN010','Azul','2013','1.0','Gasolina','180000',10),(11,'KKK-1111','Corolla','Toyota','CH011','M011','VIN011','Gris','2019','1.8','Hibrido','55000',11),(12,'LLL-1212','Sail','Chevrolet','CH012','M012','VIN012','Rojo','2018','1.5','Gasolina','70000',12),(13,'MMM-1313','Cerato','Kia','CH013','M013','VIN013','Blanco','2020','1.6','Gasolina','40000',13),(15,'OOO-1515','Fortuner','Toyota','CH015','M015','VIN015','Plata','2021','4.0','Gasolina','25000',15),(16,'PPP-1616','Santa Fe','Hyundai','CH016','M016','VIN016','Gris','2016','2.4','Gasolina','105000',16),(17,'QQQ-1717','Spark','Chevrolet','CH017','M017','VIN017','Rojo','2012','1.0','Gasolina','190000',17),(18,'RRR-1818','Seltos','Kia','CH018','M018','VIN018','Amarillo','2023','1.6','Gasolina','10000',18),(19,'SSS-1919','Creta','Hyundai','CH019','M019','VIN019','Azul','2022','1.6','Gasolina','15000',19),(20,'TTT-2020','Rav4','Toyota','CH020','M020','VIN020','Verde','2017','2.5','Hibrido','95000',20);
/*!40000 ALTER TABLE `vehiculo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vista_clientes_vip`
--

DROP TABLE IF EXISTS `vista_clientes_vip`;
/*!50001 DROP VIEW IF EXISTS `vista_clientes_vip`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_clientes_vip` AS SELECT 
 1 AS `cedula`,
 1 AS `nombre_cliente`,
 1 AS `cantidad_servicios`,
 1 AS `total_gastado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_factura_final`
--

DROP TABLE IF EXISTS `vista_factura_final`;
/*!50001 DROP VIEW IF EXISTS `vista_factura_final`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_factura_final` AS SELECT 
 1 AS `factura_nro`,
 1 AS `descripcion`,
 1 AS `mantenimiento`,
 1 AS `kilometraje`,
 1 AS `subtotal`,
 1 AS `iva`,
 1 AS `total`,
 1 AS `nombre`,
 1 AS `cedula`,
 1 AS `direccion`,
 1 AS `telefono`,
 1 AS `placa`,
 1 AS `marca`,
 1 AS `modelo`,
 1 AS `anio`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_historial_vehicular`
--

DROP TABLE IF EXISTS `vista_historial_vehicular`;
/*!50001 DROP VIEW IF EXISTS `vista_historial_vehicular`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_historial_vehicular` AS SELECT 
 1 AS `Nro Orden`,
 1 AS `Fecha`,
 1 AS `Placa`,
 1 AS `Vehiculo`,
 1 AS `Dueño`,
 1 AS `Tipo Servicio`,
 1 AS `Detalles`,
 1 AS `Kilometraje`,
 1 AS `Costo Total`,
 1 AS `Estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_ingresos_detallados`
--

DROP TABLE IF EXISTS `vista_ingresos_detallados`;
/*!50001 DROP VIEW IF EXISTS `vista_ingresos_detallados`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_ingresos_detallados` AS SELECT 
 1 AS `Nro Factura`,
 1 AS `Fecha`,
 1 AS `Cliente`,
 1 AS `Placa`,
 1 AS `Servicio Principal`,
 1 AS `Subtotal`,
 1 AS `IVA`,
 1 AS `Total`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_stock_critico`
--

DROP TABLE IF EXISTS `vista_stock_critico`;
/*!50001 DROP VIEW IF EXISTS `vista_stock_critico`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_stock_critico` AS SELECT 
 1 AS `ID`,
 1 AS `Producto`,
 1 AS `Marca`,
 1 AS `Stock Actual`,
 1 AS `Precio Unitario`,
 1 AS `Dinero Invertido`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vista_clientes_vip`
--

/*!50001 DROP VIEW IF EXISTS `vista_clientes_vip`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_clientes_vip` AS select `c`.`cedula` AS `cedula`,`c`.`nombre` AS `nombre_cliente`,count(`s`.`idServicio`) AS `cantidad_servicios`,round(sum((`s`.`costo` * 1.15)),2) AS `total_gastado` from ((`cliente` `c` join `vehiculo` `v` on((`c`.`idCliente` = `v`.`Cliente_idCliente`))) join `servicio` `s` on((`v`.`idVehiculo` = `s`.`Vehiculo_idVehiculo`))) group by `c`.`idCliente` order by `total_gastado` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_factura_final`
--

/*!50001 DROP VIEW IF EXISTS `vista_factura_final`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_factura_final` AS select `s`.`idServicio` AS `factura_nro`,`s`.`mantenimiento` AS `descripcion`,`s`.`descripcion` AS `mantenimiento`,`s`.`kilometraje` AS `kilometraje`,`s`.`costo` AS `subtotal`,round((`s`.`costo` * 0.15),2) AS `iva`,round((`s`.`costo` * 1.15),2) AS `total`,`c`.`nombre` AS `nombre`,`c`.`cedula` AS `cedula`,`c`.`direccion` AS `direccion`,`c`.`telefono` AS `telefono`,`v`.`placa` AS `placa`,`v`.`marca` AS `marca`,`v`.`modelo` AS `modelo`,`v`.`anio` AS `anio` from ((`servicio` `s` join `vehiculo` `v` on((`s`.`Vehiculo_idVehiculo` = `v`.`idVehiculo`))) join `cliente` `c` on((`v`.`Cliente_idCliente` = `c`.`idCliente`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_historial_vehicular`
--

/*!50001 DROP VIEW IF EXISTS `vista_historial_vehicular`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_historial_vehicular` AS select `s`.`idServicio` AS `Nro Orden`,`s`.`fecha_registro` AS `Fecha`,`v`.`placa` AS `Placa`,concat(`v`.`marca`,' ',`v`.`modelo`) AS `Vehiculo`,`c`.`nombre` AS `Dueño`,`s`.`mantenimiento` AS `Tipo Servicio`,`s`.`descripcion` AS `Detalles`,`s`.`kilometraje` AS `Kilometraje`,`s`.`costo` AS `Costo Total`,`s`.`estado` AS `Estado` from ((`servicio` `s` join `vehiculo` `v` on((`s`.`Vehiculo_idVehiculo` = `v`.`idVehiculo`))) join `cliente` `c` on((`v`.`Cliente_idCliente` = `c`.`idCliente`))) order by `s`.`fecha_registro` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_ingresos_detallados`
--

/*!50001 DROP VIEW IF EXISTS `vista_ingresos_detallados`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_ingresos_detallados` AS select `s`.`idServicio` AS `Nro Factura`,date_format(`s`.`fecha_registro`,'%Y-%m-%d') AS `Fecha`,`c`.`nombre` AS `Cliente`,`v`.`placa` AS `Placa`,`s`.`mantenimiento` AS `Servicio Principal`,`s`.`costo` AS `Subtotal`,round((`s`.`costo` * 0.15),2) AS `IVA`,round((`s`.`costo` * 1.15),2) AS `Total` from ((`servicio` `s` join `vehiculo` `v` on((`s`.`Vehiculo_idVehiculo` = `v`.`idVehiculo`))) join `cliente` `c` on((`v`.`Cliente_idCliente` = `c`.`idCliente`))) where (`s`.`estado` = 'Terminado') order by `s`.`fecha_registro` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_stock_critico`
--

/*!50001 DROP VIEW IF EXISTS `vista_stock_critico`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_stock_critico` AS select `producto`.`idProducto` AS `ID`,`producto`.`nombre` AS `Producto`,`producto`.`marca` AS `Marca`,`producto`.`stock` AS `Stock Actual`,`producto`.`precio` AS `Precio Unitario`,(`producto`.`stock` * `producto`.`precio`) AS `Dinero Invertido` from `producto` where (`producto`.`stock` <= 5) order by `producto`.`stock` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-02 21:08:12
