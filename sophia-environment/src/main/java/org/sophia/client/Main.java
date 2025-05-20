package org.sophia.client;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.sophia.editor.Editor;

public class Main {

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {

		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				try {
					Editor editor = new Editor();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

	}

}
