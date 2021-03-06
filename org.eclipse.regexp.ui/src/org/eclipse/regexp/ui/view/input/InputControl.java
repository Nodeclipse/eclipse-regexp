/*******************************************************************************
 * Copyright (c) 2013 Igor Zapletnev
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Igor Zapletnev - initial API and Implementation
 *******************************************************************************/
package org.eclipse.regexp.ui.view.input;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.regexp.ui.common.ControlUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

public class InputControl implements IHyperlinkListener {

	private Label label;
	private Text input;
	private ImageHyperlink hideBtn;

	private final InputSection inputSection;

	public InputControl(final InputSection inputSection) {
		this.inputSection = inputSection;
	}

	public void create(final Composite composite, final FormToolkit toolkit,
			final int index) {
		label = toolkit.createLabel(composite, "");
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).applyTo(label);

		input = toolkit.createText(composite, "", SWT.BORDER | SWT.MULTI
				| SWT.WRAP | SWT.V_SCROLL);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
				.grab(true, false).hint(SWT.DEFAULT, 3 * input.getLineHeight())
				.applyTo(input);

		if (index != 1) {
			hideBtn = toolkit.createImageHyperlink(composite, SWT.NONE);
			hideBtn.setImage(PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_ETOOL_DELETE));
			hideBtn.addHyperlinkListener(this);
			GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP)
					.applyTo(hideBtn);
		} else {
			toolkit.createLabel(composite, "");
		}

		refresh(index);
	}

	public void hide() {
		doHide(label);
		doHide(input);
		doHide(hideBtn);
	}

	public void refresh(final int index) {
		label.setText("Input " + index + ":");
	}

	private void doHide(final Control control) {
		final GridData data = (GridData) control.getLayoutData();
		data.exclude = true;
		control.setVisible(false);
		inputSection.removeInput(this);
		inputSection.refresh();
	}

	public IObservableValue observeInput() {
		return ControlUtils.observeText(input);
	}

	@Override
	public void linkEntered(final HyperlinkEvent e) {
	}

	@Override
	public void linkExited(final HyperlinkEvent e) {
	}

	@Override
	public void linkActivated(final HyperlinkEvent e) {
		hide();
	}

	public String getInput() {
		return input.getText();
	}
}