DROP PROCEDURE IF EXISTS franojan_sbd.Turnir_Brisi_Test;
CREATE PROCEDURE franojan_sbd.`Turnir_Brisi_Test`(p_turnir_id int)
BEGIN
	declare l_turnir_uuid varchar(36);
  
  select uuid into l_turnir_uuid
    from TURNIRI
    where id = p_turnir_id;
    
  delete from TURNIRI_REZULTATI
    where UUID_TURNIR = l_turnir_uuid;
    
  delete from TURNIRI_BORDOVI
    where UUID_TURNIR = l_turnir_uuid;
    
  delete from TURNIRI
    where uuid = l_turnir_uuid;
    
END;

