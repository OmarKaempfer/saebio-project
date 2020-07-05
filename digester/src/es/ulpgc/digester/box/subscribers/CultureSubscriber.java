package es.ulpgc.digester.box.subscribers;

import es.ulpgc.digester.box.DigesterBox;

public class CultureSubscriber implements java.util.function.Consumer<es.ulpgc.datahub.events.Culture> {
	private DigesterBox box;

	public CultureSubscriber(DigesterBox box) {
		this.box = box;
	}

	public void accept(es.ulpgc.datahub.events.Culture event) {

	}
}