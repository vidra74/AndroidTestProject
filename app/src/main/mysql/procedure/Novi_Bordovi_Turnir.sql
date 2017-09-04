DROP PROCEDURE IF EXISTS franojan_sbd.Novi_Bordovi_Turnir;
CREATE PROCEDURE franojan_sbd.`Novi_Bordovi_Turnir`(IN `p_id_turnir` INT, IN `p_broj_bordova` INT)
BEGIN

	DECLARE l_id INT DEFAULT 0;
  DECLARE l_id_bord INT DEFAULT 0;
  DECLARE l_brojac INT DEFAULT 0;
  DECLARE l_uuid_turnir VARCHAR(36);
  DECLARE l_uuid_bord VARCHAR(36);
  
  select count(*) into l_brojac 
    from TURNIRI_BORDOVI
    where id_turnir = p_id_turnir;
    
  if (l_brojac > 0) then
	  
    delete from TURNIRI_BORDOVI
      where id_turnir = p_id_turnir and rbr_bord > p_broj_bordova;   
  end if;
  
  
  select UUID into l_uuid_turnir 
		  from TURNIRI
		  where ID = p_id_turnir;
      
  select count(*) into l_brojac 
    from TURNIRI_BORDOVI;
  
  if (l_brojac = 0) then
  
    set l_id_bord = 1; 
  else
  
    select (max(id_bord) + 1) into l_id_bord 
      from TURNIRI_BORDOVI;  
  end if;
  
  set l_id = 1;  
  while (l_id <= p_broj_bordova) do
  
    select count(*) into l_brojac
      from TURNIRI_BORDOVI
      where id_turnir = p_id_turnir and rbr_bord = l_id;
      
    if l_brojac = 0 then
    
      INSERT INTO `TURNIRI_BORDOVI`
        (`ID_BORD`, `ID_TURNIR`, `RBR_BORD`, `UUID_TURNIR`, `UUID_BORD`, `DLM`, `VRIJEME`) 
        VALUES 
        (l_id_bord, p_id_turnir, l_id, l_uuid_turnir, uuid(), "", curtime());
        
      set l_id_bord = l_id_bord + 1;  
    end if;
    
    set l_id = l_id + 1;
    
  end while;

END;

