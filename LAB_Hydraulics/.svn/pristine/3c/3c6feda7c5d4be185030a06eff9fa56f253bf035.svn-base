package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	protected Element[] sys = new Element[100];
	protected int tot_elem = 0;
	
	public void addElement(Element elem){
		sys[tot_elem++] = elem;
	}
	
	public void deleteElement(int i) {
		sys[i] = null;
		tot_elem--;
	}
	
	/**
	 * returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){
		Element[] componentitmp = new Element[tot_elem];
		for(int i = 0;  i < tot_elem; i++) {
			componentitmp[i] = this.sys[i];
		}
		return componentitmp;
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	
//	/**
//	 * starts the simulation of the system
//	 */
//	public void simulate(SimulationObserver observer){
//		for(int i = 0; i<tot_elem; i++) {
//			if(sys[i] instanceof Source) {
//				observer.notifyFlow("Source", sys[i].getName(), SimulationObserver.NO_FLOW, ((Source) sys[i]).flow);
//				simulateR(observer, sys[i].getOutput(), ((Source) sys[i]).flow);
//			}
//		}
//	}
//	
//	public void simulateR(SimulationObserver observer, Element e, double inFlow) {
//		if(e instanceof Tap && ((Tap) e).open == true) {
//			observer.notifyFlow("Tap", e.getName(), inFlow, inFlow);
//			simulateR(observer, e.getOutput(), inFlow);
//		}
//		else if(e instanceof Tap && ((Tap) e).open == false) {
//			observer.notifyFlow("Tap", e.getName(), inFlow, SimulationObserver.NO_FLOW);
//		}
//		else if(e instanceof Split) {
//			Element[] out = new Element[2];
//			out = ((Split)e).getOutputs();
//			observer.notifyFlow("Split", e.getName(), inFlow, inFlow/2, inFlow/2);
//			simulateR(observer, out[0], inFlow/2);
//			simulateR(observer, out[1], inFlow/2);
//		}
//		else if(e instanceof Sink) {
//			observer.notifyFlow("Sink", e.getName(), inFlow, SimulationObserver.NO_FLOW);
//		}
//	}

	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for(Element e : sys){
			if( e != null && e instanceof Source){
				e.simulate(-1,observer);
			}
		}
	}

	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserverExt observer){
		for(Element e : sys){
			if( e != null && e instanceof Source){
				e.simulate(-1,observer);
			}
		}
	}
	
}
