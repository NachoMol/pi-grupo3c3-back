package com.explorer.equipo3.model.vm;

public interface PageableQuery {
    Integer getPagina();
    Integer getElementosPorPagina();
    String getOrdenadorPor();
    String getOrden();
}
