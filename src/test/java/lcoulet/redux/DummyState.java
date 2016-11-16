/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcoulet.redux;

/**
 *
 * @author Loic.Coulet
 */
class DummyState implements State<DummyState> {


    public DummyState() {
    }

    @Override
    public DummyState copy() {
        return new DummyState();
    }

}
