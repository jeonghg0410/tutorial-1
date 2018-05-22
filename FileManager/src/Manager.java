import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.datatransfer.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//5/15 ����Ʈ��ġ + ���̺� ��ġ �ϱ�
//5/16 ���丮 Ŭ�� �̺�Ʈ �ϱ�
//5/17 ��.. ���� �ߴµ� ���ߴ��� ��Ծ��..
//5/18 �ѱ� ���� ��ġ�� �� �ֵ��� ��
//���� �ٿ��ֱ� �ϰ� window��ġ�� �� �ߵǵ��� �ٲٱ� 5/20�� ����
//���� �ٿ��ֱ�� 5/21�ϵ�������;; 7�ð����� �ʹ� �����. ���� 62�ð�°����...
//5/22 ���ߴµ� ���̰���..? ������..?(73�ð�° �ٹ� ��)
public class Manager implements ActionListener, ClipboardOwner {
    private JPanel pa;
    private JTextField homeTextField;
    private JTable jt;
    private JComboBox comboBox1;
    private JList<String> list1;
    private JScrollPane sp;
    private JScrollPane spl;
    private JLabel jl;
    private File name;
    private File getBack;
    private String path = "C:\\";
    private String[][] data;
    private JMenuItem[] PopItem_Korea = new JMenuItem[6];
    private JMenuItem[] PopItem_English = new JMenuItem[6];
    private DefaultTableModel model;
    private int[] copy;
    private String[] Korean_columnNames = {"�̸�", "ũ��", "������ ��¥"};
    private String[] English_columnNames = {"Name", "Size", "Modified"};
    private Vector<String> copyFile;

    public Manager() {
        JFrame f = new JFrame("20108");
        PopItem_Korea[0] = new JMenuItem("������ ����");
        PopItem_Korea[1] = new JMenuItem("�����ϱ�");
        PopItem_Korea[2] = new JMenuItem("�ٿ��ֱ�");
        PopItem_Korea[3] = new JMenuItem("����");
        PopItem_English[0] = new JMenuItem("Show Item in the Folder");
        PopItem_English[1] = new JMenuItem("Copy");
        PopItem_English[2] = new JMenuItem("Paste");
        PopItem_English[3] = new JMenuItem("Delete");
        jt.setBackground(Color.white);
        jt.getTableHeader().setReorderingAllowed(false);
        sp.setPreferredSize(new Dimension(300, 100));
        spl.setPreferredSize(new Dimension(200, 100));
        f.add(pa);
        homeTextField.setText("C:\\");
        PopItem_Korea[2].setEnabled(false);
        PopItem_English[2].setEnabled(false);

        getJList();
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String clicked;
                    getBack = new File(path, "..");
                    clicked = list1.getSelectedValue();
                    if (clicked.equals("..")) {
                        try {
                            path = getBack.getCanonicalPath();
                        } catch (Exception ignored) {

                        }
                        homeTextField.setText(path);
                        getJList();
                    } else {
                        path = name.getPath() + File.separator + clicked;
                        if (path.contains("C:\\\\"))
                            path = name.getPath() + clicked;
                        homeTextField.setText(path);
                        getJList();
                    }
                } catch (NullPointerException aee) {
                    //�׳� ������ ������...
                }
            }
        });
        sp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu PopMenu = new JPopupMenu();
                    if (comboBox1.getSelectedItem() == "�ѱ�") {
                        for (int i = 0; i < 4; i++) {
                            if (i == 1) PopMenu.addSeparator();
                            if (i == 1 || i == 3) continue;
                            PopMenu.add(PopItem_Korea[i]);
                        }
                    } else {
                        for (int i = 0; i < 4; i++) {
                            if (i == 1) PopMenu.addSeparator();
                            if (i == 1 || i == 3) continue;
                            PopMenu.add(PopItem_English[i]);
                        }
                    }
                    PopMenu.show(jt, e.getX(), e.getY());
                    PopMenu.setVisible(true);
                }
            }
        });

        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu PopMenu = new JPopupMenu();
                    if (comboBox1.getSelectedItem() == "�ѱ�") {
                        for (int i = 0; i < 4; i++) {
                            if (i == 1 || i == 3) PopMenu.addSeparator();
                            PopMenu.add(PopItem_Korea[i]);
                        }
                    } else {
                        for (int i = 0; i < 4; i++) {
                            if (i == 1 || i == 3) PopMenu.addSeparator();
                            PopMenu.add(PopItem_English[i]);
                        }
                    }
                    PopMenu.show(jt, e.getX(), e.getY());
                    PopMenu.setVisible(true);
                }
            }
        });
        for (int i = 0; i < 4; i++) {
            PopItem_English[i].addActionListener(this);
            PopItem_Korea[i].addActionListener(this);
        }
        f.setSize(750, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        comboBox1.addActionListener(e -> setTable());
    }

    @Override
    public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
        //do nothing
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == PopItem_Korea[3] || e.getSource() == PopItem_English[3]) {
            int[] columns = jt.getSelectedRows();
            for (int column : columns) {
                System.out.println(column);
                System.out.println(path + File.separator + jt.getValueAt(column, 0));
            }
            for (int column : columns) {
                File delete = new File(path + File.separator + jt.getValueAt(column, 0));
                delete.delete();
            }
            for (int i = 0; i < columns.length; i++)
                model.removeRow(columns[i] - i);
            model.fireTableDataChanged();
            jt.updateUI();
        }

        if (e.getSource() == PopItem_Korea[0] || e.getSource() == PopItem_English[0]) {
            File open_Directory = new File(path);
            try {
                Desktop.getDesktop().open(open_Directory);
            } catch (IOException ignored) {

            }
        }

        if (e.getSource() == PopItem_Korea[1] || e.getSource() == PopItem_English[1]) {
            PopItem_Korea[2].setEnabled(true);
            PopItem_English[2].setEnabled(true);
            copy = jt.getSelectedRows();
            copyFile = new Vector<>(jt.getRowCount());

            for (int i = 0; i < copy.length; i++) {
                copyFile.add(i, (path + "\\" + jt.getValueAt(copy[i], 0)));
            }
        }

        if (e.getSource() == PopItem_Korea[2] || e.getSource() == PopItem_English[2]) {
            String tmp = path;
            int count = 0;
            for (int i = 0; i < copy.length; i++) {
                String command = "cmd /c copy \"" + copyFile.get(i) + "\"" + " \"" + tmp + "\" /y";
                try {
                    Process child = Runtime.getRuntime().exec(command);
                    InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
                    int c;
                    StringBuilder result;
                    result = new StringBuilder();
                    while ((c = in.read()) != -1) {
                        result.append((char) c);
                    }
                    if (result.toString().contains("0�� ������ ����Ǿ����ϴ�.")) {
                        if (comboBox1.getSelectedItem() == "�ѱ�")
                            JOptionPane.showMessageDialog(null, "�׼��� ������ �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(null, "You don't have access", "Error", JOptionPane.ERROR_MESSAGE);
                        in.close();
                        break;
                    }
                    in.close();
                    count++;
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            getJList();
            if (count == copy.length) {
                homeTextField.setText(path);
                getJList();
            }
        }
    }

    //����Ʈ ��ġ ���Ⱑ �ſ�ſ�ſ�ſ�ſ�ſ�ſ� ȭ����
    private void getJList() {
        //  /home/ �� �ش�
        File[] directory_list;
        File[] file_list;
        name = new File(path);

        directory_list = name.listFiles(File::isDirectory);
        file_list = name.listFiles(File::isFile);

        //���� �޾ƿ���
        //NULLPOINT dialog �������� ��
        String[] directoryName_List = new String[0];
        if (directory_list != null) {
            directoryName_List = new String[directory_list.length + 1];
            for (int i = -1; i < directory_list.length; i++) {
                String back = "..";
                if (i == -1) directoryName_List[0] = back;
                else {
                    if (directory_list[i].getName().contains("$") ||
                            directory_list[i].getName().contains("Recovery") ||
                            directory_list[i].getName().contains("System") ||
                            directory_list[i].getName().contains("Temp") ||
                            directory_list[i].getName().contains("PerfLogs") ||
                            directory_list[i].getName().contains("Documents and Settings") ||
                            !directory_list[i].canRead()) continue;

                    directoryName_List[i + 1] = directory_list[i].getName();
                }
            }
        }

        list1.setListData(directoryName_List);
        if (list1.getVisibleRowCount() != 0)
            data = new String[0][3];
        if (file_list != null) {
            {
                data = new String[file_list.length][3];
                for (int i = 0; i < file_list.length; i++) {
                    data[i][0] = file_list[i].getName();
                    String file_size;
                    long size = file_list[i].length();
                    if (size < 1024) {
                        file_size = String.format("%d B", size);
                    } else if (size < 1024 * 1024) {
                        file_size = String.format("%.2f KB", size / 1024.0);
                    } else if (size < 1024 * 1024 * 1024) {
                        file_size = String.format("%.2f MB", size / 1048576.0);
                    } else {
                        file_size = String.format("%.2f GB", size / 1073741824.0);
                    }
                    data[i][1] = file_size; // ó���� ���ڿ�
                    Date dt = new Date(file_list[i].lastModified());
                    SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy HH:mm:ss");
                    String date = formatter.format(dt);
                    data[i][2] = String.valueOf(date);
                }
            }

            setTable();
        } else {//5/20 ������� �ٽ�����
            if (comboBox1.getSelectedItem() == "�ѱ�")
                JOptionPane.showMessageDialog(null, "�׼��� ������ �����ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(null, "You don't have access", "Error", JOptionPane.ERROR_MESSAGE);
            getBack = new File(path, "..");
            try {
                path = getBack.getCanonicalPath();
            } catch (Exception ignored) {

            }
            homeTextField.setText(path);
            getJList();

        }

    }

    private void setTable() {
        if (comboBox1.getSelectedItem() == "�ѱ�") {
            model = new DefaultTableModel(data, Korean_columnNames);
            jt.setModel(model);
            jl.setText("���� �Ŵ���");
        }
        if (comboBox1.getSelectedItem() == "English") {
            model = new DefaultTableModel(data, English_columnNames);
            jt.setModel(model);
            jl.setText("File Manager");
        }
    }

    public static void main(String[] args) {
        new Manager();
    }
}
