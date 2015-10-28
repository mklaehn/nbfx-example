/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nbfx.examples.tweetwall;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JTextField;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.tweetwallfx.embeddable.EmbeddedTweetwall;

@TopComponent.Description(preferredID = "TweetwallTopComponent", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.nbfx.examples.tweetwall.TweetwallTopComponent")
@ActionReference(path = "Menu/Window", position = 1100 * 1000)
@TopComponent.OpenActionRegistration(displayName = "#CTL_TweetwallAction", preferredID = "TweetwallTopComponent")
@NbBundle.Messages({
    "CTL_TweetwallAction=Tweetwall",
    "CTL_TweetwallTopComponent=Tweetwall Window",
    "HINT_TweetwallTopComponent=This is a Tweetwall window"
})
public final class TweetwallTopComponent extends TopComponent {

    private final EmbeddedTweetwall tweetwall;
    private final JTextField queryTextfield;

    public TweetwallTopComponent() {
        super.setLayout(new BorderLayout());
        setName(NbBundle.getMessage(TweetwallTopComponent.class, "CTL_TweetwallTopComponent"));
        setToolTipText(NbBundle.getMessage(TweetwallTopComponent.class, "HINT_TweetwallTopComponent"));

        this.queryTextfield = new JTextField("#javaone");
        this.tweetwall = new EmbeddedTweetwall();

        final JFXPanel p = new JFXPanel();
        p.setScene(new Scene(tweetwall));

        queryTextfield.addActionListener(e -> {
            stop();
            start(queryTextfield.getText());
        });
        
        super.add(queryTextfield, BorderLayout.NORTH);
        super.add(p, BorderLayout.CENTER);
    }

    @Override
    protected void componentOpened() {
        start(queryTextfield.getText());
    }

    @Override
    protected void componentClosed() {
        stop();
    }

    private void start(final String query) {
        System.out.println(query);
        Platform.runLater(() -> tweetwall.start(query));
    }

    private void stop() {
        Platform.runLater(() -> tweetwall.stop());
    }
}
