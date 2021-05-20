/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Users;
import com.mycompany.services.ServiceApprenant;
import java.util.ArrayList;

/**
 *
 * @author Benzarti
 */
public class ListeApprenantForm extends BaseForm {

    Form current;
    String data = "";

    public ListeApprenantForm(Resources res) {

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("The SQUAD");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {
        });
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        addTab(swipe, s1, res.getImage("image.jpg"), "", "", res);
        //CODE DE DECORATION

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];

        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Home", barGroup);
        mesListes.setUIID("SelectBar");

        RadioButton partage = RadioButton.createToggle("Gestion Apprenant", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((b) -> {
            InfiniteProgress ipp = new InfiniteProgress();
            final Dialog ipDlg = ipp.showInifiniteBlocking();
            refreshTheme();
        });

        mesListes.addActionListener((b) -> {
            new ActionsTodo(res).show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, partage),
                FlowLayout.encloseBottom(arrow)));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowposition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);

        bindButtonSelection(partage, arrow);
        addOrientationListener(e -> {
            updateArrowposition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        Button btnAnnuler = new Button("Add Apprenant");
        addStringValue("", btnAnnuler);
        btnAnnuler.addActionListener(c -> {
            new AjoutApprenantForm(res).show();
        });

        Button pdf = new Button("pdf");
        addStringValue("", pdf);

        pdf.addPointerPressedListener(l -> {
            String urlab = "http://localhost/pdf/ex.php";
            for (Users q : new ServiceApprenant().affichageApprenant()) {

                data += "<tr> "
                        + "<td>"
                        + q.getNom()
                        + "</td>"
                        + "<td>"
                        + q.getPrenom()
                        + "</td>"
                        + "<td>"
                        + q.getPhoto()
                        + "</td>"
                        + "</tr>";
            }

            ConnectionRequest cnreq = new ConnectionRequest();
            cnreq.setPost(false);

            cnreq.addArgument("data", data);
            cnreq.setUrl(urlab);

            cnreq.addResponseListener(evx
                    -> {
                String dataw = new String(cnreq.getResponseData());
                Dialog dig = new Dialog("PDF");
                dig.show("PDF", "PDF", null, "oui");
            }
            );
            NetworkManager.getInstance().addToQueueAndWait(cnreq);

        });

        ArrayList<Users> list = ServiceApprenant.getInstance().affichageApprenant();

        for (Users u : list) {
            addButton(u, res);
        }

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));

    }

    private void addButton(Users u, Resources res) {

        //Container cnt=BorderLayout.west(null);
        Form f = new Form("Form", BoxLayout.y());

        Label tt = new Label("**********************Apprenant************** ");
        TextArea ta = new TextArea("Votre nom :" + u.getNom());
        ta.setEditable(false);
        TextArea ta2 = new TextArea("Votre prenom :" + u.getPrenom());
        ta2.setEditable(false);
        TextArea ta3 = new TextArea("Votre Photo :" + u.getPhoto());
        ta3.setEditable(false);
        TextArea ta4 = new TextArea("Votre Email :" + u.getEmail());
        ta4.setEditable(false);

        Label supprimer = new Label("Supprimer");
        supprimer.setUIID("NewsToLine");
        Style supprimerstyle = new Style(supprimer.getUnselectedStyle());
        supprimerstyle.setFgColor(0xf21f1f);
        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerstyle);
        supprimer.setIcon(supprimerImage);
        supprimer.setTextPosition(RIGHT);

        supprimer.addPointerPressedListener(l -> {

            Dialog dig = new Dialog("supprimer");
            if (dig.show("supprimer", "are you sure ? ", "NOOOO", "yep")) {
                dig.dispose();
            } else {
                dig.dispose();
            }
            try {
                if (ServiceApprenant.getInstance().deleteApprenant(u.getId())) {

                }

            } catch (Exception e) {
                System.out.println("no");
            }
            new ListeApprenantForm(res).show();

        });
        Label modifier = new Label("Modifier");
        modifier.setUIID("NewsToLine");
        Style modifierstyle = new Style(modifier.getUnselectedStyle());
        modifierstyle.setFgColor(0xf7ad02);

        FontImage fontImagemodif = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierstyle);
        modifier.setIcon(fontImagemodif);
        modifier.setTextPosition(LEFT);
        modifier.addPointerPressedListener(l
                -> {
            new ModifierApprenant(res, u).show();
        });

//cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(tt),BoxLayout.encloseX(ta),BoxLayout.encloseX(ta2),BoxLayout.encloseX(ta3),BoxLayout.encloseX(ta4),BoxLayout.encloseX(supprimer)));
        f.addAll(tt, ta, ta2, ta3, ta4, supprimer, modifier);
        add(f);

    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        System.out.println("size howa = " + size);
        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label("", "ImageOverlay");

        Container pagel
                = LayeredLayout.encloseIn(imageScale,
                        overlay,
                        BorderLayout.south(BoxLayout.encloseY(
                                new SpanLabel(text, "LargeWhiteText"),
                                FlowLayout.encloseIn(),
                                spacer
                        )));
        swipe.addTab("", res.getImage("back-logo.jpeg"), pagel);

    }

    public void bindButtonSelection(Button btn, Label l) {
        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowposition(btn, l);
            }
        });
    }

    private void updateArrowposition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth());
        l.getParent().repaint();
    }

}
