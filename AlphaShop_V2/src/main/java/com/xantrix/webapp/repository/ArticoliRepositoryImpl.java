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
		String Sql = "CALL Sp_InsArt ('" + 
				 articolo.getCodArt() + "','" + 
				 articolo.getDescrizione().replace("'", "''") + "','" + 
				 articolo.getUm() + "','" +
				 articolo.getCodStat() + "'," +
				 articolo.getPzCart() + "," + 
				 articolo.getPesoNetto() + "," + 
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

}



/*  ALTENATIVA ALL'UTILIZZO DEL METODO query
@SuppressWarnings("unchecked")
@Override
public List<Articoli> SelArticoliByFilter(String filtro)
{
    SimpleJdbcCall jdbcCall;
    
    final String result1 = "result1";
    final Map<String, Object> paramMap = new HashMap<>();
    
    //jdbcTemplate.setResultsMapCaseInsensitive(true);
    
    jdbcCall = new SimpleJdbcCall(jdbcTemplate)
    		.withProcedureName("Sp_SelArt");
    
    jdbcCall.declareParameters(
              new SqlParameter("par1", Types.CHAR),
              new SqlParameter("par2", Types.CHAR));
    		 
    
    //final Map<String, List<Articoli>> articoliInfoMap = new HashMap<>();
    Map<String, Object> results;
    List<Articoli> articoli;
    
    jdbcCall.returningResultSet(
    	    result1,
    	    (ResultSet row, int rowNum) -> 
    	  {
    	      final Articoli myArticoli = new Articoli();

    	      myArticoli.setRiga(row.getInt("RIGA"));  
    	      myArticoli.setCodArt(row.getString("CODART").trim());
    	      myArticoli.setDescrizione(row.getString("DESCRIZIONE").trim());
    	      myArticoli.setPrezzo(row.getDouble("PREZZO"));
    	      myArticoli.setUm(row.getString("UM"));
    	      myArticoli.setCodStat(row.getString("CODSTAT").trim()); 
    	      myArticoli.setPzCart(row.getInt("PZCART"));
    	      myArticoli.setPesoNetto(row.getDouble("PESONETTO"));
    	      myArticoli.setIdIva(row.getInt("IDIVA"));
    	      myArticoli.setQtaMag(row.getFloat("QTAMAG"));  
    	      myArticoli.setIdStatoArt(row.getString("IDSTATOART").trim());
    	      myArticoli.setIdFamAss(row.getInt("IDFAMASS"));
    	      myArticoli.setDesFamAss(row.getString("FAMASS").trim());
    	      myArticoli.setDataCreaz(row.getDate("DATACREAZIONE"));
    	      myArticoli.setPrezzoKg(row.getDouble("PRZKG"));

    	      return myArticoli;
    	  });
	 
    paramMap.put("par1", "Barilla");
    paramMap.put("par2", "1");
    
    results = jdbcCall.execute(paramMap);
    articoli = (List<Articoli>) results.get(result1);
    
	return articoli;
}
*/