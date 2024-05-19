package com.example.documentation.ui;

import com.example.documentation.model.Dimension;
import com.example.documentation.service.DimensionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DimensionUI extends JFrame {
    private DimensionService dimensionService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;

    public DimensionUI() {
        dimensionService = new DimensionService();
        setTitle("Documentation Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initializeUIComponents();
        loadDimensions();
    }

    private void initializeUIComponents() {
        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Description"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form setup
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        idField = new JTextField();
        nameField = new JTextField();
        descriptionField = new JTextField();
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        add(formPanel, BorderLayout.NORTH);

        // Buttons setup
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleAddDimension();
                } catch (Exception ex) {
                    showErrorDialog(ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleUpdateDimension();
                } catch (Exception ex) {
                    showErrorDialog(ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleDeleteDimension();
                } catch (Exception ex) {
                    showErrorDialog(ex);
                }
            }
        });
    }

    private void loadDimensions() {
        new SwingWorker<List<Dimension>, Void>() {
            @Override
            protected List<Dimension> doInBackground() throws Exception {
                return dimensionService.getAllDimensions();
            }

            @Override
            protected void done() {
                try {
                    List<Dimension> dimensions = get();
                    tableModel.setRowCount(0);
                    for (Dimension dimension : dimensions) {
                        tableModel.addRow(new Object[]{dimension.getId(), dimension.getName(), dimension.getDescription()});
                    }
                } catch (Exception ex) {
                    showErrorDialog(ex);
                }
            }
        }.execute();
    }

    private void handleAddDimension() {
        Dimension dimension = new Dimension();
        dimension.setId(Long.parseLong(idField.getText()));
        dimension.setName(nameField.getText());
        dimension.setDescription(descriptionField.getText());
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dimensionService.addDimension(dimension);
                return null;
            }

            @Override
            protected void done() {
                loadDimensions();
            }
        }.execute();
    }

    private void handleUpdateDimension() {
        Dimension dimension = new Dimension();
        dimension.setId(Long.parseLong(idField.getText()));
        dimension.setName(nameField.getText());
        dimension.setDescription(descriptionField.getText());
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dimensionService.updateDimension(dimension);
                return null;
            }

            @Override
            protected void done() {
                loadDimensions();
            }
        }.execute();
    }

    private void handleDeleteDimension() {
        long id = Long.parseLong(idField.getText());
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                dimensionService.deleteDimension(id);
                return null;
            }

            @Override
            protected void done() {
                loadDimensions();
            }
        }.execute();
    }

    private void showErrorDialog(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DimensionUI().setVisible(true);
            }
        });
    }
}