package com.huawei.git;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class GitSomeUtils {
    public void gitCloneAndCleanAndPush(){
        Git git = null;
        File file = new File("D:\\tmp\\test");
        CredentialsProvider cp = new UsernamePasswordCredentialsProvider("stonecomes", "shi4926422");
        CloneCommand cc = Git.cloneRepository().setCredentialsProvider(cp).setDirectory(file).setURI("https://github.com/StoneComes/hwcse");
        cc.setTimeout(30);
        try {
            git = cc.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        System.out.println("clone ok!");
        File dir = git.getRepository().getDirectory().getParentFile();
        File[] files = dir.listFiles();
        RmCommand rm = git.rm().setCached(false);
        for (File file2 : files) {
            rm.addFilepattern(file2.getName());
        }
        try {
            rm.call();
            git.commit().setMessage("clean ").setAll(true).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        System.out.println("clean ok!");

        try {
            git.add().addFilepattern(".").call();
            git.commit().setMessage("code commit").setAll(true).call();
            PushCommand push = git.push().setCredentialsProvider(cp).setRemote("origin").setTimeout(30);
            push.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        System.out.println("push OK!");
    }
}
