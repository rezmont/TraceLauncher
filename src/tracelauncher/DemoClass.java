/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tracelauncher;

class DemoClass {

    public String demoMethod(String demoParam) {
        System.out.println("Parameter passed: " + demoParam);

        return DemoClass.class.getName();
    }
}
