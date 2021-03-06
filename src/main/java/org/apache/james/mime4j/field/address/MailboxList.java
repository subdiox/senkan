package org.apache.james.mime4j.field.address;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MailboxList extends AbstractList<Mailbox> implements Serializable {
    private static final long serialVersionUID = 1;
    private final List<Mailbox> mailboxes;

    public MailboxList(List<Mailbox> mailboxes, boolean dontCopy) {
        if (mailboxes != null) {
            if (!dontCopy) {
                Object mailboxes2 = new ArrayList(mailboxes);
            }
            this.mailboxes = mailboxes;
            return;
        }
        this.mailboxes = Collections.emptyList();
    }

    public int size() {
        return this.mailboxes.size();
    }

    public Mailbox get(int index) {
        return (Mailbox) this.mailboxes.get(index);
    }

    public void print() {
        for (int i = 0; i < size(); i++) {
            System.out.println(get(i).toString());
        }
    }
}
