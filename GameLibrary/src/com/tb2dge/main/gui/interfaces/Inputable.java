package com.tb2dge.main.gui.interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.tb2dge.main.util.enums.KeyType;
import com.tb2dge.main.util.enums.MouseType;

public interface Inputable {
	public void input(KeyEvent e,KeyType kt);
	public void input(MouseEvent e,MouseType mt,int mouseX,int mouseY);
	public boolean isSelected();
}
