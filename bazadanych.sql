-- phpMyAdmin SQL Dump
-- version 3.4.4
-- http://www.phpmyadmin.net
--
-- Host: parakletos.nazwa.pl:3305
-- Czas wygenerowania: 19 Wrz 2012, 17:43
-- Wersja serwera: 5.0.91
-- Wersja PHP: 5.2.14

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Baza danych: `parakletos_5`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `aparat`
--

CREATE TABLE `aparat` (
  `id` mediumint(10) NOT NULL auto_increment,
  `id_aparatura` tinyint(3) NOT NULL,
  `id_pokoj` tinyint(3) NOT NULL,
  `typ` tinyint(1) NOT NULL default '0',
  `wartosc` smallint(3) NOT NULL default '0',
  `nazwa` varchar(20) NOT NULL default '-',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=137 ;

--
-- Zrzut danych tabeli `aparat`
--

INSERT INTO `aparat` (`id`, `id_aparatura`, `id_pokoj`, `typ`, `wartosc`, `nazwa`) VALUES
(127, 1, 11, 0, 0, '-'),
(126, 1, 60, 0, 1, '-'),
(125, 1, 1, 0, 0, '-'),
(124, 1, 1, 0, 1, '-'),
(123, 1, 61, 0, 1, '-'),
(128, 2, 61, 0, 1, 'asd'),
(129, 3, 61, 0, 55, '-'),
(130, 4, 11, 0, 0, '-'),
(131, 3, 11, 1, 68, '-'),
(132, 2, 11, 0, 0, '-'),
(133, 1, 84, 0, 0, '-'),
(134, 3, 84, 1, 59, '-'),
(135, 4, 60, 1, 0, '-'),
(136, 4, 60, 1, 0, '-');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `aparatura`
--

CREATE TABLE `aparatura` (
  `id` tinyint(1) NOT NULL auto_increment,
  `nazwa` varchar(20) NOT NULL,
  `nazwa_pl` varchar(20) NOT NULL,
  `typ` tinyint(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=5 ;

--
-- Zrzut danych tabeli `aparatura`
--

INSERT INTO `aparatura` (`id`, `nazwa`, `nazwa_pl`, `typ`) VALUES
(1, 'lights', 'światło', 0),
(2, 'windows', 'okno', 0),
(3, 'fans', 'wentylator', 1),
(4, 'heaters', 'kaloryfer', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `czujniki`
--

CREATE TABLE `czujniki` (
  `id_cz` int(11) NOT NULL auto_increment,
  `nazwa_cz` varchar(30) NOT NULL,
  PRIMARY KEY  (`id_cz`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=15 ;

--
-- Zrzut danych tabeli `czujniki`
--

INSERT INTO `czujniki` (`id_cz`, `nazwa_cz`) VALUES
(1, 'temperatura1'),
(2, 'temperatura2'),
(3, 'temperatura3');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `funkcje_p`
--

CREATE TABLE `funkcje_p` (
  `id_f` int(11) NOT NULL auto_increment,
  `nazwa_f` varchar(30) NOT NULL,
  PRIMARY KEY  (`id_f`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=6 ;

--
-- Zrzut danych tabeli `funkcje_p`
--

INSERT INTO `funkcje_p` (`id_f`, `nazwa_f`) VALUES
(1, 'gauss'),
(2, 'trapezowa'),
(3, 'dzwonowa'),
(4, 'z'),
(5, 's');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `pokoj`
--

CREATE TABLE `pokoj` (
  `id` tinyint(3) NOT NULL auto_increment,
  `nazwa` varchar(20) NOT NULL,
  `id_poziom` tinyint(3) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=85 ;

--
-- Zrzut danych tabeli `pokoj`
--

INSERT INTO `pokoj` (`id`, `nazwa`, `id_poziom`) VALUES
(1, 'wiata', 0),
(11, 'jadalnia', 2),
(10, 'strych', 4),
(9, 'pokój', 2),
(8, 'test', 1),
(61, 'garaż na motor', 0),
(60, 'łaźnia', 1),
(84, 'korytarz', 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `pomiary`
--

CREATE TABLE `pomiary` (
  `id_p` int(5) NOT NULL auto_increment,
  `id_pom` int(5) default NULL,
  `czas` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `wartosc` float NOT NULL,
  PRIMARY KEY  (`id_p`,`czas`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=11 ;

--
-- Zrzut danych tabeli `pomiary`
--

INSERT INTO `pomiary` (`id_p`, `id_pom`, `czas`, `wartosc`) VALUES
(1, 0, '2012-07-11 09:33:29', 0),
(2, 3, '0000-00-00 00:00:00', 200),
(3, 2, '0000-00-00 00:00:00', 100),
(4, 1, '0000-00-00 00:00:00', 250),
(5, 2, '0000-00-00 00:00:00', 400),
(6, 3, '0000-00-00 00:00:00', 500),
(7, 1, '0000-00-00 00:00:00', 550),
(8, 2, '0000-00-00 00:00:00', 660),
(9, 2, '0000-00-00 00:00:00', 680),
(10, 3, '0000-00-00 00:00:00', 720);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `poziom`
--

CREATE TABLE `poziom` (
  `id` mediumint(9) NOT NULL auto_increment,
  `nazwa` varchar(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=10 ;

--
-- Zrzut danych tabeli `poziom`
--

INSERT INTO `poziom` (`id`, `nazwa`) VALUES
(0, 'podwórko'),
(1, 'piwnica'),
(2, 'parter'),
(3, '1. piętro'),
(4, '2. piętro'),
(5, '3. piętro'),
(6, '4. piętro'),
(7, '5. piętro');

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `reguly`
--

CREATE TABLE `reguly` (
  `id_r` int(5) NOT NULL auto_increment,
  `id_czuj` int(5) default NULL,
  `funkcja` int(5) NOT NULL,
  `wartosc` float NOT NULL,
  `czas` float NOT NULL,
  PRIMARY KEY  (`id_r`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin2 AUTO_INCREMENT=6 ;

--
-- Zrzut danych tabeli `reguly`
--

INSERT INTO `reguly` (`id_r`, `id_czuj`, `funkcja`, `wartosc`, `czas`) VALUES
(1, 1, 0, 19, 0),
(2, 1, 0, 20, 0),
(3, 1, 0, 21, 0),
(4, 2, 1, 22, 0),
(5, 3, 1, 18, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `temperatury`
--

CREATE TABLE `temperatury` (
  `id` varchar(11) NOT NULL,
  `wartosc` float NOT NULL,
  `data` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin2;

--
-- Zrzut danych tabeli `temperatury`
--

INSERT INTO `temperatury` (`id`, `wartosc`, `data`) VALUES
('1', 14.375, '2012-09-17 11:32:03'),
('2', 20.75, '2012-09-17 11:32:03');
