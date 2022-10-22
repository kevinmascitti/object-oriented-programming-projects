package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem{
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		StringBuffer res = new StringBuffer();
		for(Element e : sys) {
			if(e != null && e instanceof Source) {
					res.append(e.layout(""));
			}
		}
		return res.toString();
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public void deleteElement(String name) {
		int i=0;
		for(Element e : sys) {
			if(e.getName().equals(name)) {
				if(e instanceof Split || e instanceof Multisplit)
					return;
				for(Element f : sys) {
					i++;
					if(f.getOutput()!=null && f.getOutput().equals(e)) {
						f.connect(e.getOutput());
						this.deleteElement(i);
						return;
					}
				}
			}
		}
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		for(Element e : sys){
			if( e != null && e instanceof Source){
				e.simulate(-1,observer,enableMaxFlowCheck);
			}
		}
	}
	
}
