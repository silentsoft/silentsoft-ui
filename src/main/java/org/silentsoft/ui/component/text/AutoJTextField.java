package org.silentsoft.ui.component.text;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AutoJTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1721385295372597718L;

	private static final String PROPERTY_ADJUST = "PROPERTY_ADJUST";

	private boolean isAdjusting() {
		if (getClientProperty(PROPERTY_ADJUST) instanceof Boolean) {
			return (Boolean) getClientProperty(PROPERTY_ADJUST);
		}
		return false;
	}

	private void setAdjusting(boolean adjust) {
		putClientProperty(PROPERTY_ADJUST, adjust);
	}
	
	@SuppressWarnings("unchecked")
	public void setAutoComplete(ArrayList<String> items, ListCellRenderer<? super String> renderer) {
		final DefaultComboBoxModel model = new DefaultComboBoxModel();

		final JComboBox<String> cbInput = new JComboBox<String>(model) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		setAdjusting(false);
		for (String item : items) {
			model.addElement(item);
		}
		cbInput.setSelectedItem(null);
		cbInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isAdjusting()) {
					if (cbInput.getSelectedItem() != null) {
						setText(cbInput.getSelectedItem().toString());
					}
				}
			}
		});

		if (renderer != null) {
			cbInput.setRenderer(renderer);
		}

		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				setAdjusting(true);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (cbInput.isPopupVisible()) {
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER
						|| e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.setSource(cbInput);
					cbInput.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						setText(cbInput.getSelectedItem().toString());
						cbInput.setPopupVisible(false);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cbInput.setPopupVisible(false);
				}
				setAdjusting(false);
			}
		});
		this.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				updateList();
			}

			public void removeUpdate(DocumentEvent e) {
				updateList();
			}

			public void changedUpdate(DocumentEvent e) {
				updateList();
			}

			private void updateList() {
				setAdjusting(true);
				model.removeAllElements();
				String input = getText();
				if (!input.isEmpty()) {
					for (String item : items) {
						if (item.toLowerCase().startsWith(input.toLowerCase())) {
							model.addElement(item);
						}
					}
				}
				cbInput.hidePopup();
				cbInput.setPopupVisible(model.getSize() > 0);
				setAdjusting(false);
			}
		});
		this.setLayout(new BorderLayout());
		this.add(cbInput, BorderLayout.SOUTH);
	}
}
