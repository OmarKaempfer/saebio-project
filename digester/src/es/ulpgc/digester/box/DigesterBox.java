package es.ulpgc.digester.box;

public class DigesterBox extends AbstractBox {

	public DigesterBox(String[] args) {
		super(args);
	}

	public DigesterBox(DigesterConfiguration configuration) {
		super(configuration);
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		super.put(o);
		return this;
	}


	public void beforeStart() {
	}

	public void afterStart() {

	}

	public void beforeStop() {

	}

	public void afterStop() {

	}
}