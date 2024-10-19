import cn.com.webxml.MobileCodeWS;
import cn.com.webxml.MobileCodeWSSoap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MobileCodeQueryApp extends JFrame {
    private JTextField mobileTextField;
    private JTextArea resultTextArea;
    private JButton queryButton;

    public MobileCodeQueryApp() {
        setTitle("手机号码归属地查询");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建面板
        JPanel panel = new JPanel(new BorderLayout());

        // 创建手机号码输入框
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        mobileTextField = new JTextField();
        inputPanel.add(new JLabel("请输入手机号码："));
        inputPanel.add(mobileTextField);
        panel.add(inputPanel, BorderLayout.NORTH);

        // 创建查询按钮
        queryButton = new JButton("查询");
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mobileCode = mobileTextField.getText().trim();
                if (mobileCode.length() >= 7) {
                    String result = getMobileCodeInfo(mobileCode);
                    resultTextArea.setText(result);
                } else {
                    JOptionPane.showMessageDialog(MobileCodeQueryApp.this, "请输入有效的手机号码（至少7位）", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(queryButton, BorderLayout.CENTER);

        // 创建结果显示区域
        resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panel.add(scrollPane, BorderLayout.SOUTH);

        add(panel);
    }

    private String getMobileCodeInfo(String mobileCode) {
        try {
            // 创建 Web Service 客户端对象
            MobileCodeWS service = new MobileCodeWS();
            MobileCodeWSSoap soap = service.getMobileCodeWSSoap();

            // 调用 Web Service 方法
            return soap.getMobileCodeInfo(mobileCode, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败：" + e.getMessage();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MobileCodeQueryApp app = new MobileCodeQueryApp();
            app.setVisible(true);
        });
    }
}
