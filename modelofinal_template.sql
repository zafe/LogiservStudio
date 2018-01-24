-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: modelofinal
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ACOPLADO`
--

DROP TABLE IF EXISTS `ACOPLADO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACOPLADO` (
  `idAcoplado` int(11) NOT NULL AUTO_INCREMENT,
  `Marca` varchar(45) DEFAULT NULL,
  `Patente` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idAcoplado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACOPLADO`
--

LOCK TABLES `ACOPLADO` WRITE;
/*!40000 ALTER TABLE `ACOPLADO` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACOPLADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ARTICULO`
--

DROP TABLE IF EXISTS `ARTICULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ARTICULO` (
  `idArticulo` int(11) NOT NULL AUTO_INCREMENT,
  `Marca` varchar(20) DEFAULT NULL,
  `Modelo` varchar(20) DEFAULT NULL,
  `Descripcion` varchar(45) DEFAULT NULL,
  `CATEGORIA_ARTICULO_idCategoriaArticulo` int(11) NOT NULL,
  `stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`idArticulo`,`CATEGORIA_ARTICULO_idCategoriaArticulo`),
  KEY `fk_ARTICULO_CATEGORIA_ARTICULO1_idx` (`CATEGORIA_ARTICULO_idCategoriaArticulo`),
  CONSTRAINT `fk_ARTICULO_CATEGORIA_ARTICULO1` FOREIGN KEY (`CATEGORIA_ARTICULO_idCategoriaArticulo`) REFERENCES `CATEGORIA_ARTICULO` (`idCategoriaArticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTICULO`
--

LOCK TABLES `ARTICULO` WRITE;
/*!40000 ALTER TABLE `ARTICULO` DISABLE KEYS */;
INSERT INTO `ARTICULO` VALUES (1,'Man','klj','joijo	',1,2);
/*!40000 ALTER TABLE `ARTICULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CAMION`
--

DROP TABLE IF EXISTS `CAMION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CAMION` (
  `idCamion` int(11) NOT NULL AUTO_INCREMENT,
  `Marca` varchar(45) DEFAULT NULL,
  `Modelo` varchar(45) DEFAULT NULL,
  `PATENTE` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`idCamion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CAMION`
--

LOCK TABLES `CAMION` WRITE;
/*!40000 ALTER TABLE `CAMION` DISABLE KEYS */;
/*!40000 ALTER TABLE `CAMION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CATEGORIA_ARTICULO`
--

DROP TABLE IF EXISTS `CATEGORIA_ARTICULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CATEGORIA_ARTICULO` (
  `idCategoriaArticulo` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCategoria` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCategoriaArticulo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CATEGORIA_ARTICULO`
--

LOCK TABLES `CATEGORIA_ARTICULO` WRITE;
/*!40000 ALTER TABLE `CATEGORIA_ARTICULO` DISABLE KEYS */;
INSERT INTO `CATEGORIA_ARTICULO` VALUES (1,'Insumos');
/*!40000 ALTER TABLE `CATEGORIA_ARTICULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CATEGORIA_EMPLEADO`
--

DROP TABLE IF EXISTS `CATEGORIA_EMPLEADO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CATEGORIA_EMPLEADO` (
  `idCategoriaEmpleado` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCategoria` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCategoriaEmpleado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CATEGORIA_EMPLEADO`
--

LOCK TABLES `CATEGORIA_EMPLEADO` WRITE;
/*!40000 ALTER TABLE `CATEGORIA_EMPLEADO` DISABLE KEYS */;
INSERT INTO `CATEGORIA_EMPLEADO` VALUES (1,'Administradores'),(2,'Conductores');
/*!40000 ALTER TABLE `CATEGORIA_EMPLEADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENTE`
--

DROP TABLE IF EXISTS `CLIENTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLIENTE` (
  `idCLIENTE` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `CUIT` varchar(45) DEFAULT NULL,
  `DOMICILIO_idDomicilio` int(11) NOT NULL,
  PRIMARY KEY (`idCLIENTE`,`DOMICILIO_idDomicilio`),
  KEY `fk_CLIENTE_DOMICILIO1_idx` (`DOMICILIO_idDomicilio`),
  CONSTRAINT `fk_CLIENTE_DOMICILIO1` FOREIGN KEY (`DOMICILIO_idDomicilio`) REFERENCES `DOMICILIO` (`idDomicilio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENTE`
--

LOCK TABLES `CLIENTE` WRITE;
/*!40000 ALTER TABLE `CLIENTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLIENTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMPRA_ARTICULO`
--

DROP TABLE IF EXISTS `COMPRA_ARTICULO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMPRA_ARTICULO` (
  `idFacturaCompraArticulo` int(11) NOT NULL AUTO_INCREMENT,
  `Fecha` date DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `PROVEEDOR_idPROVEEDOR` int(11) NOT NULL,
  PRIMARY KEY (`idFacturaCompraArticulo`,`PROVEEDOR_idPROVEEDOR`),
  KEY `fk_COMPRA_ARTICULO_PROVEEDOR1_idx` (`PROVEEDOR_idPROVEEDOR`),
  CONSTRAINT `fk_COMPRA_ARTICULO_PROVEEDOR1` FOREIGN KEY (`PROVEEDOR_idPROVEEDOR`) REFERENCES `PROVEEDOR` (`idPROVEEDOR`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMPRA_ARTICULO`
--

LOCK TABLES `COMPRA_ARTICULO` WRITE;
/*!40000 ALTER TABLE `COMPRA_ARTICULO` DISABLE KEYS */;
/*!40000 ALTER TABLE `COMPRA_ARTICULO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CONCEPTO_SUELDO`
--

DROP TABLE IF EXISTS `CONCEPTO_SUELDO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CONCEPTO_SUELDO` (
  `idCodigoConcepto` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  `cantidad` float DEFAULT NULL,
  `tipo_concepto` enum('REMUNERATIVO','NO REMUNERATIVO','RETENCION') DEFAULT NULL,
  `tipo_cantidad` enum('PORCENTAJE','FIJO','UNIDAD') DEFAULT NULL,
  PRIMARY KEY (`idCodigoConcepto`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CONCEPTO_SUELDO`
--

LOCK TABLES `CONCEPTO_SUELDO` WRITE;
/*!40000 ALTER TABLE `CONCEPTO_SUELDO` DISABLE KEYS */;
INSERT INTO `CONCEPTO_SUELDO` VALUES (1,'Comisión Carga Transportada',15,'NO REMUNERATIVO','PORCENTAJE'),(2,'Ley 19.032',3,'RETENCION','PORCENTAJE'),(3,'Obra Social',3,'RETENCION','PORCENTAJE'),(4,'Seguro Sepelio',1.5,'RETENCION','PORCENTAJE'),(5,'Jubilación',11,'RETENCION','PORCENTAJE'),(6,'Sueldo Básico',9150,'REMUNERATIVO','FIJO');
/*!40000 ALTER TABLE `CONCEPTO_SUELDO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DETALLE_COMPRA`
--

DROP TABLE IF EXISTS `DETALLE_COMPRA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DETALLE_COMPRA` (
  `idCompraArticulo` int(11) NOT NULL AUTO_INCREMENT,
  `Cantidad` int(11) DEFAULT NULL,
  `PrecioUnitario` double DEFAULT NULL,
  `Articulo_idArticulo` int(11) NOT NULL,
  `FacturaCompraArticulo_idFacturaCompraArticulo` int(11) NOT NULL,
  PRIMARY KEY (`idCompraArticulo`,`Articulo_idArticulo`,`FacturaCompraArticulo_idFacturaCompraArticulo`),
  KEY `fk_CompraArticulo_Articulo1_idx` (`Articulo_idArticulo`),
  KEY `fk_CompraArticulo_FacturaCompraArticulo1_idx` (`FacturaCompraArticulo_idFacturaCompraArticulo`),
  CONSTRAINT `fk_CompraArticulo_Articulo1` FOREIGN KEY (`Articulo_idArticulo`) REFERENCES `ARTICULO` (`idArticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CompraArticulo_FacturaCompraArticulo1` FOREIGN KEY (`FacturaCompraArticulo_idFacturaCompraArticulo`) REFERENCES `COMPRA_ARTICULO` (`idFacturaCompraArticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DETALLE_COMPRA`
--

LOCK TABLES `DETALLE_COMPRA` WRITE;
/*!40000 ALTER TABLE `DETALLE_COMPRA` DISABLE KEYS */;
/*!40000 ALTER TABLE `DETALLE_COMPRA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DETALLE_LIQUIDACION_EMPLEADO`
--

DROP TABLE IF EXISTS `DETALLE_LIQUIDACION_EMPLEADO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DETALLE_LIQUIDACION_EMPLEADO` (
  `idDetalleLiquidacionEmpleado` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` double DEFAULT NULL,
  `LiquidacionEmpleado_idLiquidacionEmpleado` int(11) NOT NULL,
  `CONCEPTO_SUELDO_idCodigoConcepto` int(11) NOT NULL,
  `monto` double DEFAULT NULL,
  PRIMARY KEY (`idDetalleLiquidacionEmpleado`,`LiquidacionEmpleado_idLiquidacionEmpleado`,`CONCEPTO_SUELDO_idCodigoConcepto`),
  KEY `fk_DetalleLiquidacionEmpleado_LiquidacionEmpleado1_idx` (`LiquidacionEmpleado_idLiquidacionEmpleado`),
  KEY `fk_DETALLE_LIQUIDACION_EMPLEADO_CONCEPTO_SUELDO1_idx` (`CONCEPTO_SUELDO_idCodigoConcepto`),
  CONSTRAINT `fk_DETALLE_LIQUIDACION_EMPLEADO_CONCEPTO_SUELDO1` FOREIGN KEY (`CONCEPTO_SUELDO_idCodigoConcepto`) REFERENCES `CONCEPTO_SUELDO` (`idCodigoConcepto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_DetalleLiquidacionEmpleado_LiquidacionEmpleado1` FOREIGN KEY (`LiquidacionEmpleado_idLiquidacionEmpleado`) REFERENCES `LIQUIDACION_EMPLEADO` (`idLiquidacionEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DETALLE_LIQUIDACION_EMPLEADO`
--

LOCK TABLES `DETALLE_LIQUIDACION_EMPLEADO` WRITE;
/*!40000 ALTER TABLE `DETALLE_LIQUIDACION_EMPLEADO` DISABLE KEYS */;
/*!40000 ALTER TABLE `DETALLE_LIQUIDACION_EMPLEADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DOMICILIO`
--

DROP TABLE IF EXISTS `DOMICILIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DOMICILIO` (
  `idDomicilio` int(11) NOT NULL AUTO_INCREMENT,
  `LOCALIDAD_idLocalidad` int(11) NOT NULL,
  `Calle` varchar(45) DEFAULT NULL,
  `Numero` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idDomicilio`,`LOCALIDAD_idLocalidad`),
  KEY `fk_DOMICILIO_LOCALIDAD1_idx` (`LOCALIDAD_idLocalidad`),
  CONSTRAINT `fk_DOMICILIO_LOCALIDAD1` FOREIGN KEY (`LOCALIDAD_idLocalidad`) REFERENCES `LOCALIDAD` (`idLocalidad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DOMICILIO`
--

LOCK TABLES `DOMICILIO` WRITE;
/*!40000 ALTER TABLE `DOMICILIO` DISABLE KEYS */;
INSERT INTO `DOMICILIO` VALUES (1,2,'Sarmiento','S/N'),(2,3,'San Martín','530'),(3,4,'Av. Belgrano','1275');
/*!40000 ALTER TABLE `DOMICILIO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EMPLEADO`
--

DROP TABLE IF EXISTS `EMPLEADO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EMPLEADO` (
  `idEmpleado` int(11) NOT NULL AUTO_INCREMENT,
  `CUIT` varchar(13) DEFAULT NULL,
  `CATEGORIA_EMPLEADO_idCategoriaEmpleado` int(11) NOT NULL,
  `hijos` int(11) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Apellido` varchar(45) DEFAULT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `DOMICILIO_idDomicilio` int(11) NOT NULL,
  PRIMARY KEY (`idEmpleado`,`CATEGORIA_EMPLEADO_idCategoriaEmpleado`,`DOMICILIO_idDomicilio`),
  KEY `fk_EMPLEADO_CATEGORIA_EMPLEADO1_idx` (`CATEGORIA_EMPLEADO_idCategoriaEmpleado`),
  KEY `fk_EMPLEADO_DOMICILIO1_idx` (`DOMICILIO_idDomicilio`),
  CONSTRAINT `fk_EMPLEADO_CATEGORIA_EMPLEADO1` FOREIGN KEY (`CATEGORIA_EMPLEADO_idCategoriaEmpleado`) REFERENCES `CATEGORIA_EMPLEADO` (`idCategoriaEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_EMPLEADO_DOMICILIO1` FOREIGN KEY (`DOMICILIO_idDomicilio`) REFERENCES `DOMICILIO` (`idDomicilio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EMPLEADO`
--

LOCK TABLES `EMPLEADO` WRITE;
/*!40000 ALTER TABLE `EMPLEADO` DISABLE KEYS */;
INSERT INTO `EMPLEADO` VALUES (1,'20367439018',1,0,'Omar Fernando','Zafe','1992-08-12',1),(2,'2021336082',1,3,'José Omar','Zafe','1970-01-22',1),(3,'20383494348',2,2,'Emanuel','Cordoba','1994-11-23',2);
/*!40000 ALTER TABLE `EMPLEADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FACTURA_VENTA`
--

DROP TABLE IF EXISTS `FACTURA_VENTA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FACTURA_VENTA` (
  `idFACTURA_VENTA` int(11) NOT NULL AUTO_INCREMENT,
  `CUIT` varchar(45) DEFAULT NULL,
  `FechaEmisión` date DEFAULT NULL,
  `CLIENTE_idCLIENTE` int(11) NOT NULL,
  PRIMARY KEY (`idFACTURA_VENTA`,`CLIENTE_idCLIENTE`),
  KEY `fk_FACTURA_VENTA_CLIENTE1_idx` (`CLIENTE_idCLIENTE`),
  CONSTRAINT `fk_FACTURA_VENTA_CLIENTE1` FOREIGN KEY (`CLIENTE_idCLIENTE`) REFERENCES `CLIENTE` (`idCLIENTE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FACTURA_VENTA`
--

LOCK TABLES `FACTURA_VENTA` WRITE;
/*!40000 ALTER TABLE `FACTURA_VENTA` DISABLE KEYS */;
/*!40000 ALTER TABLE `FACTURA_VENTA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FINCA`
--

DROP TABLE IF EXISTS `FINCA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FINCA` (
  `idFinca` int(11) NOT NULL AUTO_INCREMENT,
  `coordenada` point DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idFinca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FINCA`
--

LOCK TABLES `FINCA` WRITE;
/*!40000 ALTER TABLE `FINCA` DISABLE KEYS */;
/*!40000 ALTER TABLE `FINCA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `INGENIO`
--

DROP TABLE IF EXISTS `INGENIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `INGENIO` (
  `idIngenio` int(11) NOT NULL AUTO_INCREMENT,
  `coordenada` point DEFAULT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Arranque` double DEFAULT NULL,
  `Tarifa` double DEFAULT NULL,
  PRIMARY KEY (`idIngenio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `INGENIO`
--

LOCK TABLES `INGENIO` WRITE;
/*!40000 ALTER TABLE `INGENIO` DISABLE KEYS */;
/*!40000 ALTER TABLE `INGENIO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIQUIDACION_EMPLEADO`
--

DROP TABLE IF EXISTS `LIQUIDACION_EMPLEADO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LIQUIDACION_EMPLEADO` (
  `idLiquidacionEmpleado` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_liquidacion` date DEFAULT NULL,
  `importe_neto` double DEFAULT NULL,
  `total_haberes_remunerativos` double DEFAULT NULL,
  `total_haberes_no_remunerativos` double DEFAULT NULL,
  `total_retenciones` double DEFAULT NULL,
  `total_bruto` double DEFAULT NULL,
  `EMPLEADO_idEmpleado` int(11) NOT NULL,
  `inicio_periodo` date DEFAULT NULL,
  `fin_periodo` date DEFAULT NULL,
  PRIMARY KEY (`idLiquidacionEmpleado`,`EMPLEADO_idEmpleado`),
  KEY `fk_LIQUIDACION_EMPLEADO_EMPLEADO1_idx` (`EMPLEADO_idEmpleado`),
  CONSTRAINT `fk_LIQUIDACION_EMPLEADO_EMPLEADO1` FOREIGN KEY (`EMPLEADO_idEmpleado`) REFERENCES `EMPLEADO` (`idEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIQUIDACION_EMPLEADO`
--

LOCK TABLES `LIQUIDACION_EMPLEADO` WRITE;
/*!40000 ALTER TABLE `LIQUIDACION_EMPLEADO` DISABLE KEYS */;
/*!40000 ALTER TABLE `LIQUIDACION_EMPLEADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LOCALIDAD`
--

DROP TABLE IF EXISTS `LOCALIDAD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LOCALIDAD` (
  `idLocalidad` int(11) NOT NULL AUTO_INCREMENT,
  `NombreLocalidad` varchar(45) DEFAULT NULL,
  `PROVINCIA_idProvincia` int(11) NOT NULL,
  PRIMARY KEY (`idLocalidad`,`PROVINCIA_idProvincia`),
  KEY `fk_LOCALIDAD_PROVINCIA1_idx` (`PROVINCIA_idProvincia`),
  CONSTRAINT `fk_LOCALIDAD_PROVINCIA1` FOREIGN KEY (`PROVINCIA_idProvincia`) REFERENCES `PROVINCIA` (`idProvincia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LOCALIDAD`
--

LOCK TABLES `LOCALIDAD` WRITE;
/*!40000 ALTER TABLE `LOCALIDAD` DISABLE KEYS */;
INSERT INTO `LOCALIDAD` VALUES (2,'La Florida',24),(3,'Cruz Alta',24),(4,'Delfín Gallo',24),(5,'Alderetes',24),(6,'Aguilares',24),(7,'Concepción',24),(8,'Monteros',24),(9,'León Rouges',24);
/*!40000 ALTER TABLE `LOCALIDAD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORIGEN_DESTINO`
--

DROP TABLE IF EXISTS `ORIGEN_DESTINO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORIGEN_DESTINO` (
  `idOrigen_Destino` int(11) NOT NULL AUTO_INCREMENT,
  `DistanciaKM` float DEFAULT NULL,
  `FINCA_idFinca` int(11) NOT NULL,
  `INGENIO_idIngenio` int(11) NOT NULL,
  PRIMARY KEY (`idOrigen_Destino`,`FINCA_idFinca`,`INGENIO_idIngenio`),
  KEY `fk_ORIGEN_DESTINO_FINCA1_idx` (`FINCA_idFinca`),
  KEY `fk_ORIGEN_DESTINO_INGENIO1_idx` (`INGENIO_idIngenio`),
  CONSTRAINT `fk_ORIGEN_DESTINO_FINCA1` FOREIGN KEY (`FINCA_idFinca`) REFERENCES `FINCA` (`idFinca`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORIGEN_DESTINO_INGENIO1` FOREIGN KEY (`INGENIO_idIngenio`) REFERENCES `INGENIO` (`idIngenio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORIGEN_DESTINO`
--

LOCK TABLES `ORIGEN_DESTINO` WRITE;
/*!40000 ALTER TABLE `ORIGEN_DESTINO` DISABLE KEYS */;
/*!40000 ALTER TABLE `ORIGEN_DESTINO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROVEEDOR`
--

DROP TABLE IF EXISTS `PROVEEDOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROVEEDOR` (
  `idPROVEEDOR` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `CUIT` varchar(13) DEFAULT NULL,
  `DOMICILIO_idDomicilio` int(11) NOT NULL,
  PRIMARY KEY (`idPROVEEDOR`,`DOMICILIO_idDomicilio`),
  KEY `fk_PROVEEDOR_DOMICILIO1_idx` (`DOMICILIO_idDomicilio`),
  CONSTRAINT `fk_PROVEEDOR_DOMICILIO1` FOREIGN KEY (`DOMICILIO_idDomicilio`) REFERENCES `DOMICILIO` (`idDomicilio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROVEEDOR`
--

LOCK TABLES `PROVEEDOR` WRITE;
/*!40000 ALTER TABLE `PROVEEDOR` DISABLE KEYS */;
/*!40000 ALTER TABLE `PROVEEDOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROVINCIA`
--

DROP TABLE IF EXISTS `PROVINCIA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROVINCIA` (
  `idProvincia` int(11) NOT NULL AUTO_INCREMENT,
  `NombreProvincia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idProvincia`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROVINCIA`
--

LOCK TABLES `PROVINCIA` WRITE;
/*!40000 ALTER TABLE `PROVINCIA` DISABLE KEYS */;
INSERT INTO `PROVINCIA` VALUES (1,'CABA'),(2,'Buenos Aires'),(3,'Catamarca'),(4,'Chaco'),(5,'Chubut'),(6,'Córdoba'),(7,'Corrientes'),(8,'Entre Ríos'),(9,'Formosa'),(10,'Jujuy'),(11,'La Pampa'),(12,'La Rioja'),(13,'Mendoza'),(14,'Misiones'),(15,'Neuquén'),(16,'Río Negro'),(17,'Salta'),(18,'San Juan'),(19,'San Luis'),(20,'Santa Cruz'),(21,'Santa Fe'),(22,'Santiago del Estero'),(23,'Tierra del Fuego'),(24,'Tucumán');
/*!40000 ALTER TABLE `PROVINCIA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RECIBO_SUELDO`
--

DROP TABLE IF EXISTS `RECIBO_SUELDO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RECIBO_SUELDO` (
  `idRECIBO_SUELDO` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `total_neto` double DEFAULT NULL,
  `LiquidacionEmpleado_idLiquidacionEmpleado` int(11) NOT NULL,
  PRIMARY KEY (`idRECIBO_SUELDO`,`LiquidacionEmpleado_idLiquidacionEmpleado`),
  KEY `fk_RECIBO_SUELDO_LiquidacionEmpleado1_idx` (`LiquidacionEmpleado_idLiquidacionEmpleado`),
  CONSTRAINT `fk_RECIBO_SUELDO_LiquidacionEmpleado1` FOREIGN KEY (`LiquidacionEmpleado_idLiquidacionEmpleado`) REFERENCES `LIQUIDACION_EMPLEADO` (`idLiquidacionEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RECIBO_SUELDO`
--

LOCK TABLES `RECIBO_SUELDO` WRITE;
/*!40000 ALTER TABLE `RECIBO_SUELDO` DISABLE KEYS */;
/*!40000 ALTER TABLE `RECIBO_SUELDO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REMITO_HAS_ACOPLADO`
--

DROP TABLE IF EXISTS `REMITO_HAS_ACOPLADO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REMITO_HAS_ACOPLADO` (
  `Remito_idRemito` int(11) NOT NULL,
  `Acoplado_idAcoplado` int(11) NOT NULL,
  PRIMARY KEY (`Remito_idRemito`,`Acoplado_idAcoplado`),
  KEY `fk_Remito_has_Acoplado_Acoplado1_idx` (`Acoplado_idAcoplado`),
  KEY `fk_Remito_has_Acoplado_Remito1_idx` (`Remito_idRemito`),
  CONSTRAINT `fk_Remito_has_Acoplado_Acoplado1` FOREIGN KEY (`Acoplado_idAcoplado`) REFERENCES `ACOPLADO` (`idAcoplado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Remito_has_Acoplado_Remito1` FOREIGN KEY (`Remito_idRemito`) REFERENCES `VIAJE` (`idRemito`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REMITO_HAS_ACOPLADO`
--

LOCK TABLES `REMITO_HAS_ACOPLADO` WRITE;
/*!40000 ALTER TABLE `REMITO_HAS_ACOPLADO` DISABLE KEYS */;
/*!40000 ALTER TABLE `REMITO_HAS_ACOPLADO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TIPO_LIQUIDACION`
--

DROP TABLE IF EXISTS `TIPO_LIQUIDACION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TIPO_LIQUIDACION` (
  `CATEGORIA_EMPLEADO_idCategoriaEmpleado` int(11) NOT NULL,
  `CONCEPTO_SUELDO_idCodigoConcepto` int(11) NOT NULL,
  PRIMARY KEY (`CATEGORIA_EMPLEADO_idCategoriaEmpleado`,`CONCEPTO_SUELDO_idCodigoConcepto`),
  KEY `fk_CATEGORIA_EMPLEADO_has_CONCEPTO_SUELDO_CONCEPTO_SUELDO1_idx` (`CONCEPTO_SUELDO_idCodigoConcepto`),
  KEY `fk_CATEGORIA_EMPLEADO_has_CONCEPTO_SUELDO_CATEGORIA_EMPLEAD_idx` (`CATEGORIA_EMPLEADO_idCategoriaEmpleado`),
  CONSTRAINT `fk_CATEGORIA_EMPLEADO_has_CONCEPTO_SUELDO_CATEGORIA_EMPLEADO1` FOREIGN KEY (`CATEGORIA_EMPLEADO_idCategoriaEmpleado`) REFERENCES `CATEGORIA_EMPLEADO` (`idCategoriaEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CATEGORIA_EMPLEADO_has_CONCEPTO_SUELDO_CONCEPTO_SUELDO1` FOREIGN KEY (`CONCEPTO_SUELDO_idCodigoConcepto`) REFERENCES `CONCEPTO_SUELDO` (`idCodigoConcepto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TIPO_LIQUIDACION`
--

LOCK TABLES `TIPO_LIQUIDACION` WRITE;
/*!40000 ALTER TABLE `TIPO_LIQUIDACION` DISABLE KEYS */;
/*!40000 ALTER TABLE `TIPO_LIQUIDACION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USUARIO`
--

DROP TABLE IF EXISTS `USUARIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USUARIO` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `NombreUsuario` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Empleado_idEmpleado` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`Empleado_idEmpleado`),
  KEY `fk_Usuario_Empleado1_idx` (`Empleado_idEmpleado`),
  CONSTRAINT `fk_Usuario_Empleado1` FOREIGN KEY (`Empleado_idEmpleado`) REFERENCES `EMPLEADO` (`idEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USUARIO`
--

LOCK TABLES `USUARIO` WRITE;
/*!40000 ALTER TABLE `USUARIO` DISABLE KEYS */;
INSERT INTO `USUARIO` VALUES (1,'fernando','password1234',1),(2,'jose70','clave345',2);
/*!40000 ALTER TABLE `USUARIO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VIAJE`
--

DROP TABLE IF EXISTS `VIAJE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VIAJE` (
  `idRemito` int(11) NOT NULL AUTO_INCREMENT,
  `Fecha` date DEFAULT NULL,
  `HoraEntrada` time DEFAULT NULL,
  `Bruto` double DEFAULT NULL,
  `Tara` double DEFAULT NULL,
  `Origen_Destino_idOrigen_Destino` int(11) NOT NULL,
  `Empleado_idEmpleado` int(11) NOT NULL,
  `CAMION_idCamion` int(11) NOT NULL,
  `FACTURA_VENTA_idFACTURA_VENTA` int(11) NOT NULL,
  PRIMARY KEY (`idRemito`,`Origen_Destino_idOrigen_Destino`,`Empleado_idEmpleado`,`CAMION_idCamion`,`FACTURA_VENTA_idFACTURA_VENTA`),
  KEY `fk_Remito_Origen_Destino1_idx` (`Origen_Destino_idOrigen_Destino`),
  KEY `fk_Remito_Empleado1_idx` (`Empleado_idEmpleado`),
  KEY `fk_REMITO_CAMION1_idx` (`CAMION_idCamion`),
  KEY `fk_VIAJE_FACTURA_VENTA1_idx` (`FACTURA_VENTA_idFACTURA_VENTA`),
  CONSTRAINT `fk_REMITO_CAMION1` FOREIGN KEY (`CAMION_idCamion`) REFERENCES `CAMION` (`idCamion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Remito_Empleado1` FOREIGN KEY (`Empleado_idEmpleado`) REFERENCES `EMPLEADO` (`idEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Remito_Origen_Destino1` FOREIGN KEY (`Origen_Destino_idOrigen_Destino`) REFERENCES `ORIGEN_DESTINO` (`idOrigen_Destino`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_VIAJE_FACTURA_VENTA1` FOREIGN KEY (`FACTURA_VENTA_idFACTURA_VENTA`) REFERENCES `FACTURA_VENTA` (`idFACTURA_VENTA`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VIAJE`
--

LOCK TABLES `VIAJE` WRITE;
/*!40000 ALTER TABLE `VIAJE` DISABLE KEYS */;
/*!40000 ALTER TABLE `VIAJE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-26 17:58:29
