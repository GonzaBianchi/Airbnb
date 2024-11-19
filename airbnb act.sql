-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-11-2024 a las 21:13:05
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `airbnb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `id_pais` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`id`, `nombre`, `id_pais`) VALUES
(1, 'París', 1),
(2, 'Marsella', 1),
(3, 'Lyon', 1),
(4, 'Toulouse', 1),
(5, 'Niza', 1),
(6, 'Nantes', 1),
(7, 'Estrasburgo', 1),
(8, 'Montpellier', 1),
(9, 'Burdeos', 1),
(10, 'Lille', 1),
(11, 'Madrid', 2),
(12, 'Barcelona', 2),
(13, 'Valencia', 2),
(14, 'Sevilla', 2),
(15, 'Zaragoza', 2),
(16, 'Málaga', 2),
(17, 'Bilbao', 2),
(18, 'Las Palmas de Gran Canaria', 2),
(19, 'Palma de Mallorca', 2),
(20, 'Alicante', 2),
(21, 'Nueva York', 3),
(22, 'Los Ángeles', 3),
(23, 'Chicago', 3),
(24, 'Houston', 3),
(25, 'Phoenix', 3),
(26, 'Filadelfia', 3),
(27, 'San Antonio', 3),
(28, 'San Diego', 3),
(29, 'Dallas', 3),
(30, 'San José', 3),
(31, 'Pekín', 4),
(32, 'Shanghái', 4),
(33, 'Cantón', 4),
(34, 'Shenzhen', 4),
(35, 'Chongqing', 4),
(36, 'Tianjin', 4),
(37, 'Hangzhou', 4),
(38, 'Wuhan', 4),
(39, 'Xi\'an', 4),
(40, 'Chengdu', 4),
(41, 'Roma', 5),
(42, 'Milán', 5),
(43, 'Nápoles', 5),
(44, 'Turín', 5),
(45, 'Palermo', 5),
(46, 'Génova', 5),
(47, 'Bolonia', 5),
(48, 'Florencia', 5),
(49, 'Bari', 5),
(50, 'Catania', 5),
(51, 'Estambul', 6),
(52, 'Ankara', 6),
(53, 'Izmir', 6),
(54, 'Bursa', 6),
(55, 'Adana', 6),
(56, 'Gaziantep', 6),
(57, 'Konya', 6),
(58, 'Antalya', 6),
(59, 'Kayseri', 6),
(60, 'Mersin', 6),
(61, 'Ciudad de México', 7),
(62, 'Guadalajara', 7),
(63, 'Monterrey', 7),
(64, 'Puebla', 7),
(65, 'Tijuana', 7),
(66, 'León', 7),
(67, 'Ciudad Juárez', 7),
(68, 'Cancún', 7),
(69, 'Chihuahua', 7),
(70, 'Mérida', 7),
(71, 'Bangkok', 8),
(72, 'Chiang Mai', 8),
(73, 'Phuket', 8),
(74, 'Pattaya', 8),
(75, 'Nonthaburi', 8),
(76, 'Udon Thani', 8),
(77, 'Hat Yai', 8),
(78, 'Nakhon Ratchasima', 8),
(79, 'Khon Kaen', 8),
(80, 'Surat Thani', 8),
(81, 'Berlín', 9),
(82, 'Hamburgo', 9),
(83, 'Múnich', 9),
(84, 'Colonia', 9),
(85, 'Fráncfort', 9),
(86, 'Stuttgart', 9),
(87, 'Düsseldorf', 9),
(88, 'Dortmund', 9),
(89, 'Essen', 9),
(90, 'Leipzig', 9),
(91, 'Londres', 10),
(92, 'Birmingham', 10),
(93, 'Glasgow', 10),
(94, 'Liverpool', 10),
(95, 'Mánchester', 10),
(96, 'Edimburgo', 10),
(97, 'Leeds', 10),
(98, 'Bristol', 10),
(99, 'Sheffield', 10),
(100, 'Newcastle', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hospedaje`
--

CREATE TABLE `hospedaje` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `precio_por_noche` decimal(8,2) NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_modificacion` date DEFAULT NULL,
  `id_tipo_hospedaje` int(11) NOT NULL,
  `id_ciudad` int(11) NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL,
  `borrado` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hospedaje`
--

INSERT INTO `hospedaje` (`id`, `descripcion`, `imagen`, `precio_por_noche`, `fecha_creacion`, `fecha_modificacion`, `id_tipo_hospedaje`, `id_ciudad`, `id_usuario`, `borrado`) VALUES
(1, 'Un hotel muy lindo bilbao televisor y cocina.', 'http://ejemplo.com/imagen.jpg', 50.00, '2024-10-31 20:24:08', NULL, 4, 17, 5, 0),
(2, 'Un hotel terriblemente nasheee', 'http://ejemplo22.com/imagen2.jpg', 150.00, '2024-11-18 20:09:26', NULL, 2, 15, 5, 0),
(3, 'prueba de modificacion', 'http://ejemplo3.com/imagen32.jpg', 332.00, '2024-11-18 20:16:48', '2024-11-18', 1, 3, 5, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`id`, `nombre`) VALUES
(1, 'Francia'),
(2, 'España'),
(3, 'Estados Unidos'),
(4, 'China'),
(5, 'Italia'),
(6, 'Turquía'),
(7, 'México'),
(8, 'Tailandia'),
(9, 'Alemania'),
(10, 'Reino Unido');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `id` bigint(20) NOT NULL,
  `id_hospedaje` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `fecha_check_in` date NOT NULL,
  `fecha_check_out` date NOT NULL,
  `cant_adultos` int(11) DEFAULT NULL,
  `cant_bebes` int(11) DEFAULT NULL,
  `cant_mascotas` int(11) DEFAULT NULL,
  `importe_total` decimal(10,2) NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_modificacion` datetime DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `cant_ninos` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`id`, `id_hospedaje`, `id_usuario`, `fecha_check_in`, `fecha_check_out`, `cant_adultos`, `cant_bebes`, `cant_mascotas`, `importe_total`, `fecha_creacion`, `fecha_modificacion`, `estado`, `cant_ninos`) VALUES
(1, 1, 2, '2024-02-21', '2024-02-25', 4, 1, 1, 200.00, '2024-10-31 21:12:24', '2024-10-31 22:01:52', 'Confirmada', 2),
(2, 1, 2, '2024-02-26', '2024-02-28', 4, 1, 1, 100.00, '2024-10-31 22:04:22', NULL, 'Cancelada', 2),
(3, 1, 2, '2024-03-26', '2024-03-28', 4, 1, 1, 100.00, '2024-10-31 22:04:52', '2024-10-31 22:04:46', 'Cancelada', 2),
(4, 1, 2, '2024-12-25', '2024-12-30', 4, 1, 1, 250.00, '2024-10-31 21:40:05', '2024-10-31 22:57:35', 'Pendiente', 2),
(5, 1, 2, '2024-02-10', '2024-02-15', 4, 1, 1, 250.00, '2024-10-31 21:41:10', NULL, 'Pendiente', NULL),
(6, 1, 2, '2024-02-06', '2024-02-09', 4, 1, 1, 150.00, '2024-10-31 21:42:30', NULL, 'Pendiente', NULL),
(7, 1, 2, '2024-02-04', '2024-02-05', 4, 1, 1, 50.00, '2024-10-31 21:45:05', NULL, 'Pendiente', NULL),
(8, 1, 2, '2024-02-02', '2024-02-03', 4, 1, 1, 50.00, '2024-10-31 21:50:03', NULL, 'Pendiente', 2),
(9, 1, 2, '2024-11-05', '2024-11-08', 4, 1, 1, 150.00, '2024-10-31 22:42:42', NULL, 'Pendiente', 2),
(10, 1, 2, '2024-11-25', '2024-11-30', 4, 1, 1, 250.00, '2024-10-31 22:53:58', NULL, 'Pendiente', 2),
(11, 1, 2, '2024-12-20', '2024-12-23', 4, 1, 1, 150.00, '2024-10-31 22:58:24', '2024-10-31 22:58:48', 'Pendiente', 2),
(12, 1, 2, '2024-12-14', '2024-12-16', 4, 1, 1, 100.00, '2024-10-31 22:59:08', '2024-10-31 22:59:52', 'Confirmada', 2),
(13, 1, 2, '2024-12-10', '2024-12-11', 4, 1, 1, 50.00, '2024-10-31 23:01:14', '2024-10-31 23:02:24', 'Confirmada', 2),
(14, 1, 2, '2024-12-05', '2024-12-06', 4, 1, 1, 50.00, '2024-10-31 23:03:16', '2024-10-31 23:03:52', 'Confirmada', 2),
(15, 1, 2, '2024-12-03', '2024-12-04', 4, 1, 1, 50.00, '2024-10-31 23:05:20', '2024-10-31 23:06:02', 'Confirmada', 2),
(16, 1, 2, '2024-11-23', '2024-11-24', 4, 1, 1, 50.00, '2024-10-31 23:08:11', '2024-10-31 23:12:18', 'Confirmada', 2),
(17, 1, 2, '2024-11-01', '2024-11-04', 4, 1, 1, 150.00, '2024-10-31 23:10:10', '2024-10-31 23:10:30', 'Confirmada', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `borrado` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `servicio`
--

INSERT INTO `servicio` (`id`, `nombre`, `borrado`) VALUES
(1, 'Wifi', 0),
(2, 'Cocina', 0),
(3, 'Lavarropas', 0),
(4, 'Secadora', 0),
(5, 'Aire acondicionado', 0),
(6, 'Calefacción', 0),
(7, 'Zona de trabajo', 0),
(8, 'Televisor', 0),
(9, 'Secador de pelo', 0),
(10, 'Plancha', 0),
(13, 'nashi', 0),
(15, 'Nashe', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio_hospedaje`
--

CREATE TABLE `servicio_hospedaje` (
  `id_servicio` int(11) NOT NULL,
  `id_hospedaje` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `servicio_hospedaje`
--

INSERT INTO `servicio_hospedaje` (`id_servicio`, `id_hospedaje`) VALUES
(1, 3),
(2, 1),
(3, 2),
(5, 2),
(8, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_hospedaje`
--

CREATE TABLE `tipo_hospedaje` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipo_hospedaje`
--

INSERT INTO `tipo_hospedaje` (`id`, `nombre`) VALUES
(1, 'CASA'),
(2, 'DEPARTAMENTO'),
(3, 'CABAÑA'),
(4, 'HOTEL'),
(5, 'CASA DE HUSPEDES'),
(6, 'MANSION'),
(7, 'DOMO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_usuario`
--

CREATE TABLE `tipo_usuario` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipo_usuario`
--

INSERT INTO `tipo_usuario` (`id`, `nombre`) VALUES
(1, 'ADMINISTRADOR'),
(2, 'INQUILINO'),
(3, 'ANFITRION');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `dni` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_modificacion` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `username`, `email`, `password`, `dni`, `nombre`, `apellido`, `fecha_nacimiento`, `fecha_creacion`, `fecha_modificacion`) VALUES
(1, 'osvaldo.cozzi24', 'osvaldocozzi@mail.com', '$2a$10$Tz/yJQ8myI9wXvNCxKoZ1Ozl/DkGfGSTB1Xyd7WdnVJ2pSpZZmH2e', '12345678', 'osvaldo', 'cozzi', '1999-10-24', '2024-10-29 14:03:17', NULL),
(2, 'gonza.bianch1', 'gonzabianch@mail.com', '$2a$10$m.COtiixh1TBh0GXWNnS3ud372DhKRYQ/aJ.gmERiY0gu5si2u2am', '87654321', 'gonzalo', 'bianchini', '2001-10-24', '2024-10-29 20:40:11', NULL),
(3, 'gonzibianchi123', 'gonzi@gmail.com', '$2a$10$gQsQhIK6M1v0pvCQz62kH.CayJuXB76dF0AHJMLZT6gTe5mP1zQsC', '99999995', 'gonzia', 'bianchicitoa', '2001-10-12', '2024-11-15 00:24:44', '2024-11-16 04:18:20.000000'),
(4, 'gonzitabian', 'chici@gmail.com', '$2a$10$33DRh30jjVWk9lB0o2axTuStG7W19k6aYJpWlKUv04MrjSL3/oF2m', '99999998', 'gonzalito', 'chicito', '2002-10-12', '2024-11-15 00:39:40', NULL),
(5, 'zalito123', 'zalito123@gmail.com', '$2a$10$HO4VpPhDRcMkJvCcSh1AnO3u6abTxY7GcEp2CDkOQc96HhCN4E9/O', '99999997', 'zalitos', 'zalitoz', '2003-10-12', '2024-11-15 00:49:42', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_tipo_usuario`
--

CREATE TABLE `usuario_tipo_usuario` (
  `id_tipo_usuario` int(11) NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario_tipo_usuario`
--

INSERT INTO `usuario_tipo_usuario` (`id_tipo_usuario`, `id_usuario`) VALUES
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 4),
(2, 5),
(3, 2),
(3, 4),
(3, 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pais` (`id_pais`);

--
-- Indices de la tabla `hospedaje`
--
ALTER TABLE `hospedaje`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_tipo_hospedaje` (`id_tipo_hospedaje`),
  ADD KEY `id_ciudad` (`id_ciudad`),
  ADD KEY `fk_id_usuario` (`id_usuario`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_hospedaje` (`id_hospedaje`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `servicio_hospedaje`
--
ALTER TABLE `servicio_hospedaje`
  ADD PRIMARY KEY (`id_servicio`,`id_hospedaje`),
  ADD KEY `id_hospedaje` (`id_hospedaje`);

--
-- Indices de la tabla `tipo_hospedaje`
--
ALTER TABLE `tipo_hospedaje`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `usuario_tipo_usuario`
--
ALTER TABLE `usuario_tipo_usuario`
  ADD PRIMARY KEY (`id_tipo_usuario`,`id_usuario`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT de la tabla `hospedaje`
--
ALTER TABLE `hospedaje`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `tipo_hospedaje`
--
ALTER TABLE `tipo_hospedaje`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD CONSTRAINT `ciudad_ibfk_1` FOREIGN KEY (`id_pais`) REFERENCES `pais` (`id`);

--
-- Filtros para la tabla `hospedaje`
--
ALTER TABLE `hospedaje`
  ADD CONSTRAINT `fk_id_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `hospedaje_ibfk_1` FOREIGN KEY (`id_tipo_hospedaje`) REFERENCES `tipo_hospedaje` (`id`),
  ADD CONSTRAINT `hospedaje_ibfk_2` FOREIGN KEY (`id_ciudad`) REFERENCES `ciudad` (`id`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`id_hospedaje`) REFERENCES `hospedaje` (`id`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `servicio_hospedaje`
--
ALTER TABLE `servicio_hospedaje`
  ADD CONSTRAINT `servicio_hospedaje_ibfk_1` FOREIGN KEY (`id_servicio`) REFERENCES `servicio` (`id`),
  ADD CONSTRAINT `servicio_hospedaje_ibfk_2` FOREIGN KEY (`id_hospedaje`) REFERENCES `hospedaje` (`id`);

--
-- Filtros para la tabla `usuario_tipo_usuario`
--
ALTER TABLE `usuario_tipo_usuario`
  ADD CONSTRAINT `usuario_tipo_usuario_ibfk_1` FOREIGN KEY (`id_tipo_usuario`) REFERENCES `tipo_usuario` (`id`),
  ADD CONSTRAINT `usuario_tipo_usuario_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
