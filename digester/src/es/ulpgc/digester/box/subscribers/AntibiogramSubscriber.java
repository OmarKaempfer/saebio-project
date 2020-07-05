package es.ulpgc.digester.box.subscribers;

import es.ulpgc.digester.box.DigesterBox;

public class AntibiogramSubscriber implements java.util.function.Consumer<es.ulpgc.datahub.events.Antibiogram> {
	private DigesterBox box;

	public AntibiogramSubscriber(DigesterBox box) {
		this.box = box;
	}

	public void accept(es.ulpgc.datahub.events.Antibiogram event) {

	}
}