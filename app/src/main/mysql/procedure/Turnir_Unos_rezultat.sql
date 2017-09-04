DROP PROCEDURE IF EXISTS franojan_sbd.Turnir_Unos_Rezultat;
CREATE PROCEDURE franojan_sbd.`Turnir_Unos_Rezultat`(IN p_uuid_turnir varchar(36), 
                                                      IN p_uuid_bord varchar(36), 
                                                      IN p_ns_pair INT,
                                                      IN p_ew_pair INT,
                                                      IN p_contract varchar(5),
                                                      IN p_rbr_board INT,
                                                      IN p_declarer varchar(1),
                                                      IN p_lead varchar(3),
                                                      IN p_dec_tricks INT,
                                                      IN p_ns_result INT,
                                                      IN p_is_bye BOOLEAN)
this_proc:BEGIN

  DECLARE l_provjera INT DEFAULT 0;
  DECLARE l_id INT DEFAULT 0;
  
  -- ako nije unesen rezultat i jedan od parova je 0 ne treba upload
  if (p_ns_pair = 0) and (p_ns_result = 0) then
    LEAVE this_proc;
  end if;

  if (p_ew_pair = 0) and (p_ns_result = 0) then
    LEAVE this_proc;
  end if;  
  
  -- provjeri pristigle bordove
  
  select count(*) into l_provjera 
    from TURNIRI
    where UUID = p_uuid_turnir;
  
  -- ako je nepoznat turnir_id tada nema unosa
  
  if l_provjera = 0 then
    LEAVE this_proc;
  end if;
  
  -- ako je nepoznat bord i novi rbr onda su generirani na mobitelu i dodavamo ga
  
  select count(*) into l_provjera
    from TURNIRI_BORDOVI
    where UUID_TURNIR = p_uuid_turnir and UUID_BORD = p_uuid_bord;
    
  if l_provjera = 0 then
  
    select count(*) + 1 into l_id
      from TURNIRI_BORDOVI;
      
    select count(*) into l_provjera
      from TURNIRI;
  
    INSERT INTO `TURNIRI_BORDOVI`(`ID_BORD`, `ID_TURNIR`, `RBR_BORD`, `UUID_TURNIR`, `UUID_BORD`, `DLM`, `VRIJEME`) 
    VALUES (l_id, l_provjera, p_rbr_board, p_uuid_turnir, p_uuid_bord, '', CURDATE());
  
  end if;
  
  -- ako je nepoznat bord a rbr je poznat nesto se dogodilo sa bordom, unesi samo rezultat
  -- dalje radi unos

  select count(*) into l_id
    from TURNIRI_REZULTATI
    where uuid_turnir = p_uuid_turnir and uuid_bord = p_uuid_bord and rbr_board = p_rbr_board and 
          ns_pair = p_ns_pair and ew_pair = p_ew_pair;
          
  if l_id > 0 then
    
    UPDATE `TURNIRI_REZULTATI` SET 
      `CONTRACT` = p_contract,
      `DECLARER` = p_declarer,
      `LEAD` = p_lead,
      `DECLARER_TRICKS` = p_declarer,
      `NS_REZULTAT` = p_ns_result,
      `ISBYE` = p_is_bye 
    WHERE uuid_turnir = p_uuid_turnir and uuid_bord = p_uuid_bord and rbr_board = p_rbr_board and 
          ns_pair = p_ns_pair and ew_pair = p_ew_pair;
  
  else
  
    select coalesce((select max(id_rezultat) from `TURNIRI_REZULTATI`), 0) + 1 into l_id
      from dual;
  
    INSERT INTO `TURNIRI_REZULTATI`(`ID_REZULTAT`, `UUID_BORD`, `UUID_TURNIR`, `NS_PAIR`, `EW_PAIR`, 
                                    `CONTRACT`, `RBR_BOARD`, `DECLARER`, `LEAD`, `DECLARER_TRICKS`, 
                                    `NS_REZULTAT`, `ISBYE`) 
    VALUES (l_id, p_uuid_bord, p_uuid_turnir, p_ns_pair, p_ew_pair,
            p_contract, p_rbr_board, p_declarer, p_lead, p_dec_tricks,
            p_ns_result, p_is_bye);
  end if;

END;

