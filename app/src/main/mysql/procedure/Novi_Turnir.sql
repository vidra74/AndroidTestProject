DROP PROCEDURE IF EXISTS franojan_sbd.Novi_Turnir;
CREATE PROCEDURE franojan_sbd.`Novi_Turnir`(IN `p_klub` INT, IN `p_obracun` INT, IN `p_broj_bordova` INT)
BEGIN

	DECLARE l_id INT DEFAULT 0;
  
  DECLARE l_uuid_turnir VARCHAR(36);
  
  select count(*) into l_id 
    from TURNIRI
    where DATUM = CURDATE();
    
  if (l_id > 0) then
	  
    select ID into l_id 
		  from TURNIRI
		  where DATUM = CURDATE();   
  else
  
    select (max(id) + 1) into l_id
      from TURNIRI;
      
    select uuid() into l_uuid_turnir
      from DUAL;
		
    INSERT INTO `TURNIRI`
      (`ID`, `Datum`, `Vrijeme`, `Klub`, `Obracun`, `Broj_Bordova`, `STATUS`, `UUID`) 
      VALUES 
      (l_id, CURDATE(), MAKETIME(19, 30, 0), p_klub, p_obracun, p_broj_bordova, 1, l_uuid_turnir);
      
    call Novi_Bordovi_Turnir(l_id, p_broj_bordova);

  end if;

END;

