/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjdk.jmc.netbeans;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openjdk.jmc.common.item.IItemCollection;
import org.openjdk.jmc.flightrecorder.CouldNotLoadRecordingException;
import org.openjdk.jmc.flightrecorder.JfrLoaderToolkit;
import org.openjdk.jmc.flightrecorder.rules.report.html.JfrHtmlRulesReport;

@Messages({
    "LBL_JFR_LOADER=Files of JFR"
})
@MIMEResolver.ExtensionRegistration(
        displayName = "#LBL_JFR_LOADER",
        mimeType = "application/jfr",
        extension = {"jfr"}
)
@DataObject.Registration(
        mimeType = "application/jfr",
        iconBase = "org/openjdk/jmc/netbeans/jfr.png",
        displayName = "#LBL_JFR_LOADER",
        position = 300
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/application/jfr/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class JFRDataObject extends MultiDataObject {

    public JFRDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("application/jfr", true);  
        
    }

    @Override
    protected int associateLookup() {
        return 1;
    }
    
    String getHTMLReport() {
        String content = "Nothing to bee seen here";
        try (InputStream in = getPrimaryFile().getInputStream()) {
            IItemCollection events = JfrLoaderToolkit.loadEvents(in);
            content = JfrHtmlRulesReport.createReport(events);
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException | CouldNotLoadRecordingException ex) {
            Exceptions.printStackTrace(ex);
        }
        return content;
    }

    @MultiViewElement.Registration(
            displayName = "#LBL_JFR_EDITOR",
            iconBase = "org/openjdk/jmc/netbeans/mission_control_16.png",
            mimeType = "application/jfr",
            persistenceType = TopComponent.PERSISTENCE_NEVER,
            preferredID = "JFR",
            position = 1000
    )
    @Messages("LBL_JFR_EDITOR=JMC Report")
    public static JFRVisualElement createEditor(Lookup lkp) {
        return new JFRVisualElement(lkp);
    }
    
}
