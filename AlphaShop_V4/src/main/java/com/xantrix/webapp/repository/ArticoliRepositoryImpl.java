package com.xantrix.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xantrix.webapp.domain.Articoli;

@Repository 
public class ArticoliRepositoryImpl implements ArticoliRepository
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	 
	@Override
	public List<Articoli> SelArticoliByFilter(String Filtro)
	{
		
		String IdListino = "1"; //TODO Parametrizzare il Listino
		
		String Sql = "CALL Sp_SelArt ('" + Filtro + "','" + IdListino + "');";
		
		List<Articoli> articoli = jdbcTemplate.query(Sql, new ArticoliMapper());
		
		return articoli;
	}
	
	@Override
	public void InsArticolo(Articoli articolo)
	{
		
		String PesoNetto = Double.toString(articolo.getPesoNetto()).replaceAll(",", ".");
		//String CodArt =  String.format("%9s", articolo.getCodArt()).replace(' ', '0');
		String CodArt = articolo.getCodArt().trim();
		String DesArt = articolo.getDescrizione().replace("'", "''").trim();
		
		String Sql = "CALL Sp_InsArt ('" + 
				 CodArt + "','" + 
				 DesArt + "','" + 
				 articolo.getUm() + "','" +
				 articolo.getCodStat() + "'," +
				 articolo.getPzCart() + "," + 
				 PesoNetto + "," + 
				 articolo.getIdIva() + "," + 
				 articolo.getIdStatoArt() + "," +
				 articolo.getIdFamAss() + ")";
		 
		 jdbcTemplate.update(Sql);
		
	}

	@Override
	public void DelArticolo(String CodArt)
	{
		String Sql = "CALL Sp_DelArt ('" + CodArt + "')";
		
		jdbcTemplate.update(Sql);
		
	}
	
	private boolean isNumeric(String str)
	{ 
		return str.matches("-?\\d+(\\.\\d+)?");   
	} 


	

}
