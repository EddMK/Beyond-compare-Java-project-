/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kuo
 */
public class ElemDateTimeCell extends ElemCell<LocalDateTime> {
   
    @Override
    String texte(LocalDateTime elem) {
        return elem.format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"));
    }
    
}

