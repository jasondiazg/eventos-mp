/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.eventos.web;

import mp.eventos.dao.EventoDao;
import mp.eventos.model.Evento;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

@ManagedBean
@SessionScoped
public class EventoManagedBean implements Serializable {

    @Inject
    EventoDao eventoService;

    private Evento evento;

    @PostConstruct
    public void init() {
        evento = new Evento();
    }

    public Evento getEvento() {
        return evento;
    }

    public String addEvent() {
        System.out.println("Saving " + evento);
        eventoService.create(evento);
        return "confirm-evento.xhtml";
    }
}
