/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robertjpa2;

import java.util.List;

/**
 *
 * @author robert
 */
public class RobertJPA2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        tlcJpaController cont = new tlcJpaController();
        TLowerCaseJpaController c2 = new TLowerCaseJpaController();
        int l = cont.gettlcCount();
        List<TLowerCase> li = c2.findTLowerCaseEntities();
        System.out.println(c2.findTLowerCase(1).getNameValue());
        System.out.println(l);
    }
}
