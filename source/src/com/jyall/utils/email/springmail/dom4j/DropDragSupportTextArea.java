package com.jyall.utils.email.springmail.dom4j;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JTextArea;

public class DropDragSupportTextArea
        extends JTextArea
        implements DropTargetListener
{
    private DropTarget dropTarget;

    public DropDragSupportTextArea()
    {
        this.dropTarget = new DropTarget(this, 3,
                this, true);
    }

    public void dragEnter(DropTargetDragEvent dtde)
    {
        DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();
        if (dataFlavors[0].match(DataFlavor.javaFileListFlavor)) {
            try
            {
                Transferable tr = dtde.getTransferable();
                Object obj = tr.getTransferData(DataFlavor.javaFileListFlavor);
                List<File> files = (List)obj;
                for (int i = 0; i < files.size(); i++) {
                    append(((File)files.get(i)).getAbsolutePath().trim());
                }
            }
            catch (UnsupportedFlavorException localUnsupportedFlavorException) {}catch (IOException localIOException) {}
        }
    }

    public void dragOver(DropTargetDragEvent dtde) {}

    public void dropActionChanged(DropTargetDragEvent dtde) {}

    public void dragExit(DropTargetEvent dte) {}

    public void drop(DropTargetDropEvent dtde) {}
}
