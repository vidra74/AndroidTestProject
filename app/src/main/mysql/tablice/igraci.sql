CREATE TABLE `IGRACI` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IME` varchar(30) COLLATE utf8_croatian_ci NOT NULL,
  `PREZIME` varchar(40) COLLATE utf8_croatian_ci NOT NULL,
  `MAIL` varchar(100) COLLATE utf8_croatian_ci NOT NULL,
  `MOBITEL` varchar(30) COLLATE utf8_croatian_ci NOT NULL,
  `KLUB` int(11) NOT NULL,
  `AKTIVAN` int(11) NOT NULL,
  `UUID` varchar(36) COLLATE utf8_croatian_ci NOT NULL,
  `VRIJEME` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

