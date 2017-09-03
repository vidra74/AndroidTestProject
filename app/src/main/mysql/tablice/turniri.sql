CREATE TABLE `TURNIRI` (
  `ID` int(11) NOT NULL,
  `Datum` date NOT NULL,
  `Vrijeme` time NOT NULL,
  `Klub` int(11) NOT NULL,
  `Obracun` int(11) NOT NULL,
  `Broj_Bordova` int(11) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `UUID` varchar(36) COLLATE utf8_croatian_ci NOT NULL COMMENT '8-4-4-4-12 format string',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci COMMENT='Tablica sa popisom turnira';

