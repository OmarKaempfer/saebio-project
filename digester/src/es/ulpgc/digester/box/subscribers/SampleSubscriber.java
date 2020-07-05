package es.ulpgc.digester.box.subscribers;

import es.ulpgc.digester.box.DigesterBox;

public class SampleSubscriber implements java.util.function.Consumer<es.ulpgc.datahub.events.Sample> {
	private DigesterBox box;

	public SampleSubscriber(DigesterBox box) {
		this.box = box;
	}

	public void accept(es.ulpgc.datahub.events.Sample event) {

	}
}