-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-10-2023 a las 08:30:37
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `nutricionista`
--
CREATE DATABASE IF NOT EXISTS `nutricionista` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `nutricionista`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comida`
--

CREATE TABLE `comida` (
  `idComida` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `detalle` varchar(100) NOT NULL,
  `cantCalorias` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comida`
--

INSERT INTO `comida` (`idComida`, `nombre`, `detalle`, `cantCalorias`, `estado`) VALUES
(15, 'desayuno', 'tostadas con mermelada', 250, 1),
(22, 'Fideo con salsa', 'Salsa bolognesa', 250, 1),
(23, 'Tostadas con mermelada', 'Mermelada de fresa', 200, 1),
(24, 'Sándwich de jamón y queso', 'Pan integral, jamón y queso', 350, 1),
(25, 'Yogur con frutas', 'Yogur con fresas y plátanos', 150, 1),
(26, 'Manzana', 'Manzana fresca', 80, 1),
(27, 'Pizza de pepperoni', 'Pizza con pepperoni y queso', 400, 1),
(28, 'Cereal con leche', 'Cereal de desayuno con leche', 250, 1),
(29, 'Ensalada de pollo', 'Ensalada de pollo con aderezo', 300, 1),
(30, 'Sopa de lentejas', 'Sopa casera de lentejas', 220, 1),
(31, 'Frutos secos', 'Mezcla de nueces y almendras', 180, 1),
(32, 'Salmón a la parrilla', 'Filete de salmón a la parrilla', 350, 1),
(33, 'Batido de proteínas', 'Batido con proteína en polvo', 180, 1),
(34, 'Palitos de zanahoria', 'Zanahorias crujientes', 70, 1),
(35, 'Huevo cocido', 'Huevo duro', 70, 1),
(36, 'Barra de granola', 'Barra de granola con miel', 200, 1),
(37, 'Pasta al pesto', 'Pasta con salsa pesto', 300, 1),
(38, 'Té verde', 'Té verde caliente', 0, 1),
(39, 'Hamburguesa con papas fritas', 'Hamburguesa con patatas fritas', 500, 1),
(40, 'Helado de vainilla', 'Helado de vainilla', 250, 1),
(41, 'Sopa de tomate', 'Sopa de tomate caliente', 180, 1),
(42, 'Pollo asado', 'Pollo asado con verduras', 400, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consulta`
--

CREATE TABLE `consulta` (
  `idConsulta` int(11) NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `pesoActual` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consulta`
--

INSERT INTO `consulta` (`idConsulta`, `idPaciente`, `fecha`, `pesoActual`) VALUES
(4, 16, '2023-10-11', 70.5),
(5, 16, '2023-10-15', 71.2),
(6, 16, '2023-10-20', 70.8),
(7, 16, '2023-10-25', 71.5),
(8, 16, '2023-10-30', 70.9),
(9, 17, '2023-10-11', 65.8),
(10, 17, '2023-10-15', 66.2),
(11, 17, '2023-10-20', 66.5),
(12, 17, '2023-10-25', 67.1),
(13, 17, '2023-10-30', 66.9),
(14, 18, '2023-10-11', 70.2),
(15, 18, '2023-10-15', 70.8),
(16, 18, '2023-10-20', 71.1),
(17, 18, '2023-10-25', 70.5),
(18, 18, '2023-10-30', 71),
(19, 19, '2023-10-11', 65.5),
(20, 19, '2023-10-15', 66),
(21, 19, '2023-10-20', 66.3),
(22, 19, '2023-10-25', 66.9),
(23, 19, '2023-10-30', 66.4),
(24, 27, '2023-10-11', 63.2),
(25, 27, '2023-10-15', 63.7),
(26, 27, '2023-10-20', 64.1),
(27, 27, '2023-10-25', 64.5),
(28, 27, '2023-10-30', 64),
(29, 20, '2023-10-11', 80.5),
(30, 20, '2023-10-15', 81),
(31, 20, '2023-10-20', 81.4),
(32, 20, '2023-10-25', 80.9),
(33, 20, '2023-10-30', 81.7),
(34, 21, '2023-10-11', 55.8),
(35, 21, '2023-10-15', 56.2),
(36, 21, '2023-10-20', 56.9),
(37, 21, '2023-10-25', 57.3),
(38, 21, '2023-10-30', 57),
(39, 22, '2023-10-11', 75.2),
(40, 22, '2023-10-15', 75.7),
(41, 22, '2023-10-20', 75),
(42, 22, '2023-10-25', 75.5),
(43, 22, '2023-10-30', 76.1),
(44, 23, '2023-10-11', 68.4),
(45, 23, '2023-10-15', 68.8),
(46, 23, '2023-10-20', 68.2),
(47, 23, '2023-10-25', 68.9),
(48, 23, '2023-10-30', 69.3),
(59, 26, '2023-10-11', 77.5),
(60, 26, '2023-10-15', 77.9),
(61, 26, '2023-10-20', 78.2),
(62, 26, '2023-10-25', 78.6),
(63, 26, '2023-10-30', 78.9),
(64, 25, '2023-10-11', 60.1),
(65, 25, '2023-10-15', 60.5),
(66, 25, '2023-10-20', 60.9),
(67, 25, '2023-10-25', 61.3),
(68, 25, '2023-10-30', 61.7),
(69, 24, '2023-10-11', 72.6),
(70, 24, '2023-10-15', 73),
(71, 24, '2023-10-20', 72.4),
(72, 24, '2023-10-25', 72.9),
(73, 24, '2023-10-30', 73.2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dieta`
--

CREATE TABLE `dieta` (
  `idDieta` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `pesoFinal` double NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dieta`
--

INSERT INTO `dieta` (`idDieta`, `nombre`, `idPaciente`, `fechaInicio`, `fechaFin`, `pesoFinal`, `estado`) VALUES
(21, 'Tratamiento jnz', 16, '2023-10-11', '2024-01-25', 100, 1),
(22, 'Tratamiento fghydfh', 17, '2023-10-11', '2024-01-25', 111, 1),
(23, 'Tratamiento Juan Pérez', 18, '2023-10-11', '2024-01-25', 70, 1),
(24, 'Tratamiento Luisa González', 19, '2023-10-11', '2024-01-25', 65, 1),
(25, 'Tratamiento Roberto Sánchez', 20, '2023-10-11', '2024-01-25', 80, 1),
(26, 'Tratamiento Ana López', 21, '2023-10-11', '2024-01-25', 55, 1),
(27, 'Tratamiento Pedro Ramírez', 22, '2023-10-11', '2024-01-25', 75, 1),
(28, 'Tratamiento Carmen Torres', 23, '2023-10-11', '2024-01-25', 68, 1),
(29, 'Tratamiento Diego Martínez', 24, '2023-10-11', '2024-01-25', 72, 1),
(30, 'Tratamiento Sofía Rodríguez', 25, '2023-10-11', '2024-01-25', 62, 1),
(31, 'Tratamiento Manuel Fernández', 26, '2023-10-11', '2024-01-25', 78, 1),
(32, 'Tratamiento Laura García', 27, '2023-10-11', '2024-01-25', 63, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dietacomida`
--

CREATE TABLE `dietacomida` (
  `idDietaComida` int(11) NOT NULL,
  `idComida` int(11) NOT NULL,
  `idDieta` int(11) NOT NULL,
  `porcion` int(11) NOT NULL,
  `horario` varchar(100) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dietacomida`
--

INSERT INTO `dietacomida` (`idDietaComida`, `idComida`, `idDieta`, `porcion`, `horario`, `estado`) VALUES
(28, 22, 23, 250, 'DESAYUNO', 1),
(29, 24, 23, 350, 'DESAYUNO', 1),
(30, 30, 23, 400, 'ALMUERZO', 1),
(31, 33, 23, 300, 'ALMUERZO', 1),
(32, 25, 23, 150, 'MERIENDA', 1),
(33, 26, 23, 200, 'MERIENDA', 1),
(34, 35, 23, 450, 'CENA', 1),
(35, 37, 23, 300, 'CENA', 1),
(36, 29, 23, 100, 'SNACK', 1),
(37, 32, 23, 200, 'SNACK', 1),
(38, 22, 23, 250, 'DESAYUNO', 1),
(39, 24, 23, 350, 'DESAYUNO', 1),
(40, 30, 23, 400, 'ALMUERZO', 1),
(41, 33, 23, 300, 'ALMUERZO', 1),
(42, 25, 23, 150, 'MERIENDA', 1),
(43, 26, 23, 200, 'MERIENDA', 1),
(44, 35, 23, 450, 'CENA', 1),
(45, 37, 23, 300, 'CENA', 1),
(46, 29, 23, 100, 'SNACK', 1),
(47, 32, 23, 200, 'SNACK', 1),
(48, 23, 21, 200, 'DESAYUNO', 1),
(49, 26, 21, 80, 'DESAYUNO', 1),
(50, 30, 21, 220, 'ALMUERZO', 1),
(51, 33, 21, 180, 'ALMUERZO', 1),
(52, 25, 21, 150, 'MERIENDA', 1),
(53, 26, 21, 80, 'MERIENDA', 1),
(54, 35, 21, 100, 'CENA', 1),
(55, 37, 21, 220, 'CENA', 1),
(56, 29, 21, 100, 'SNACK', 1),
(57, 32, 21, 150, 'SNACK', 1),
(58, 22, 22, 250, 'DESAYUNO', 1),
(59, 24, 22, 350, 'DESAYUNO', 1),
(60, 30, 22, 400, 'ALMUERZO', 1),
(61, 33, 22, 300, 'ALMUERZO', 1),
(62, 25, 22, 150, 'MERIENDA', 1),
(63, 26, 22, 200, 'MERIENDA', 1),
(64, 35, 22, 450, 'CENA', 1),
(65, 37, 22, 300, 'CENA', 1),
(66, 29, 22, 100, 'SNACK', 1),
(67, 32, 22, 200, 'SNACK', 1),
(68, 22, 24, 250, 'DESAYUNO', 1),
(69, 24, 24, 350, 'DESAYUNO', 1),
(70, 30, 24, 400, 'ALMUERZO', 1),
(71, 33, 24, 300, 'ALMUERZO', 1),
(72, 25, 24, 150, 'MERIENDA', 1),
(73, 26, 24, 200, 'MERIENDA', 1),
(74, 35, 24, 450, 'CENA', 1),
(75, 37, 24, 300, 'CENA', 1),
(76, 29, 24, 100, 'SNACK', 1),
(77, 32, 24, 200, 'SNACK', 1),
(78, 23, 25, 200, 'DESAYUNO', 1),
(79, 26, 25, 80, 'DESAYUNO', 1),
(80, 30, 25, 220, 'ALMUERZO', 1),
(81, 33, 25, 180, 'ALMUERZO', 1),
(82, 25, 25, 150, 'MERIENDA', 1),
(83, 26, 25, 80, 'MERIENDA', 1),
(84, 35, 25, 100, 'CENA', 1),
(85, 37, 25, 220, 'CENA', 1),
(86, 29, 25, 100, 'SNACK', 1),
(87, 32, 25, 150, 'SNACK', 1),
(88, 22, 26, 250, 'DESAYUNO', 1),
(89, 24, 26, 350, 'DESAYUNO', 1),
(90, 30, 26, 400, 'ALMUERZO', 1),
(91, 33, 26, 300, 'ALMUERZO', 1),
(92, 25, 26, 150, 'MERIENDA', 1),
(93, 26, 26, 200, 'MERIENDA', 1),
(94, 35, 26, 450, 'CENA', 1),
(95, 37, 26, 300, 'CENA', 1),
(96, 29, 26, 100, 'SNACK', 1),
(97, 32, 26, 200, 'SNACK', 1),
(98, 23, 27, 200, 'DESAYUNO', 1),
(99, 26, 27, 80, 'DESAYUNO', 1),
(100, 30, 27, 220, 'ALMUERZO', 1),
(101, 33, 27, 180, 'ALMUERZO', 1),
(102, 25, 27, 150, 'MERIENDA', 1),
(103, 26, 27, 80, 'MERIENDA', 1),
(104, 35, 27, 100, 'CENA', 1),
(105, 37, 27, 220, 'CENA', 1),
(106, 29, 27, 100, 'SNACK', 1),
(107, 32, 27, 150, 'SNACK', 1),
(108, 23, 21, 200, 'DESAYUNO', 1),
(109, 26, 21, 80, 'DESAYUNO', 1),
(110, 30, 21, 220, 'ALMUERZO', 1),
(111, 33, 21, 180, 'ALMUERZO', 1),
(112, 25, 21, 150, 'MERIENDA', 1),
(113, 26, 21, 80, 'MERIENDA', 1),
(114, 35, 21, 100, 'CENA', 1),
(115, 37, 21, 220, 'CENA', 1),
(116, 29, 21, 100, 'SNACK', 1),
(117, 32, 21, 150, 'SNACK', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

CREATE TABLE `paciente` (
  `nombreCompleto` varchar(100) NOT NULL,
  `DNI` int(11) NOT NULL,
  `domicilio` varchar(100) NOT NULL,
  `celular` int(11) NOT NULL,
  `pesoActual` double NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`nombreCompleto`, `DNI`, `domicilio`, `celular`, `pesoActual`, `idPaciente`, `estado`) VALUES
('jnz', 123456711, 'sl 25500000', 353744444, 100, 16, 1),
('fghydfh', 45235, '', 124124, 111, 17, 1),
('Juan Pérez', 123456789, 'Calle A, 123', 555111222, 70, 18, 1),
('Luisa González', 987654321, 'Calle B, 456', 555333444, 65, 19, 1),
('Roberto Sánchez', 456789123, 'Calle C, 789', 555555666, 80, 20, 1),
('Ana López', 654321987, 'Calle D, 234', 555777888, 55, 21, 1),
('Pedro Ramírez', 321987654, 'Calle E, 567', 555999000, 75, 22, 1),
('Carmen Torres', 789123456, 'Calle F, 890', 555222333, 68, 23, 1),
('Diego Martínez', 987123456, 'Calle G, 123', 555444555, 72, 24, 1),
('Sofía Rodríguez', 123987456, 'Calle H, 456', 555666777, 62, 25, 1),
('Manuel Fernández', 654789321, 'Calle I, 789', 555888999, 78, 26, 1),
('Laura García', 987654123, 'Calle J, 234', 555000111, 63, 27, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comida`
--
ALTER TABLE `comida`
  ADD PRIMARY KEY (`idComida`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`idConsulta`),
  ADD KEY `idPaciente` (`idPaciente`);

--
-- Indices de la tabla `dieta`
--
ALTER TABLE `dieta`
  ADD PRIMARY KEY (`idDieta`),
  ADD KEY `idPaciente` (`idPaciente`);

--
-- Indices de la tabla `dietacomida`
--
ALTER TABLE `dietacomida`
  ADD PRIMARY KEY (`idDietaComida`),
  ADD KEY `idComida` (`idComida`),
  ADD KEY `idDieta` (`idDieta`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`idPaciente`),
  ADD UNIQUE KEY `DNI` (`DNI`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comida`
--
ALTER TABLE `comida`
  MODIFY `idComida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT de la tabla `consulta`
--
ALTER TABLE `consulta`
  MODIFY `idConsulta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT de la tabla `dieta`
--
ALTER TABLE `dieta`
  MODIFY `idDieta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `dietacomida`
--
ALTER TABLE `dietacomida`
  MODIFY `idDietaComida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;

--
-- AUTO_INCREMENT de la tabla `paciente`
--
ALTER TABLE `paciente`
  MODIFY `idPaciente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `consulta_ibfk_1` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`);

--
-- Filtros para la tabla `dieta`
--
ALTER TABLE `dieta`
  ADD CONSTRAINT `dieta_ibfk_1` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`);

--
-- Filtros para la tabla `dietacomida`
--
ALTER TABLE `dietacomida`
  ADD CONSTRAINT `dietacomida_ibfk_1` FOREIGN KEY (`idComida`) REFERENCES `comida` (`idComida`),
  ADD CONSTRAINT `dietacomida_ibfk_2` FOREIGN KEY (`idDieta`) REFERENCES `dieta` (`idDieta`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
