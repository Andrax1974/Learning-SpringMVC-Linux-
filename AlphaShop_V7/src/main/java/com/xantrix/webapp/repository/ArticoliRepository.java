package com.xantrix.webapp.repository;

import java.util.List;

import com.xantrix.webapp.domain.Articoli;

public interface ArticoliRepository
{
	List <Articoli> SelArticoliByFilter(String Filtro);
		
	void InsArticolo(Articoli articolo);
	
	void DelArticolo(String CodArt);
}
