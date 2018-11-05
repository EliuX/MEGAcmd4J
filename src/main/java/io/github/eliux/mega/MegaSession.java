package io.github.eliux.mega;

import io.github.eliux.mega.auth.MegaAuth;
import io.github.eliux.mega.cmd.FileInfo;
import io.github.eliux.mega.cmd.MegaCmdChangePassword;
import io.github.eliux.mega.cmd.MegaCmdCopy;
import io.github.eliux.mega.cmd.MegaCmdExport;
import io.github.eliux.mega.cmd.MegaCmdGet;
import io.github.eliux.mega.cmd.MegaCmdHttps;
import io.github.eliux.mega.cmd.MegaCmdList;
import io.github.eliux.mega.cmd.MegaCmdLogout;
import io.github.eliux.mega.cmd.MegaCmdMakeDirectory;
import io.github.eliux.mega.cmd.MegaCmdMove;
import io.github.eliux.mega.cmd.MegaCmdPutMultiple;
import io.github.eliux.mega.cmd.MegaCmdPutSingle;
import io.github.eliux.mega.cmd.MegaCmdRemove;
import io.github.eliux.mega.cmd.MegaCmdSession;
import io.github.eliux.mega.cmd.MegaCmdShare;
import io.github.eliux.mega.cmd.MegaCmdWhoAmI;
import java.util.concurrent.Callable;
import java.util.function.Predicate;


/**
 * Facade of the MEGAcmd functions that works with an existing session. <p> For all those functions
 * that return an instance of AbstractMegaCmd remember to use a {@link Callable#call()} ()} if it is
 * supposed to return something or {@link Runnable#run()} if it doesn't.
 */
public class MegaSession {

  private MegaAuth authentication;

  public MegaSession(MegaAuth authentication) {
    this.authentication = authentication;
  }

  /**
   * Retrieve the existing authentication object for this session
   *
   * @return {@link MegaAuth} not null
   */
  public MegaAuth getAuthentication() {
    return authentication;
  }

  /**
   * Changes the password of the current logged user
   *
   * @param oldPassword {@link String} with the current password
   * @param newPassword {@link String} having the new password
   */
  public void changePassword(String oldPassword, String newPassword) {
    new MegaCmdChangePassword(oldPassword, newPassword).run();
    System.setProperty(Mega.PASSWORD_ENV_VAR, newPassword);
  }

  /**
   * Closes de current session
   */
  public void logout() {
    new MegaCmdLogout().run();
  }

  /**
   * Returns the ID to identify the session in MEGA
   *
   * @return {@link String} not null
   */
  public String sessionID() {
    return new MegaCmdSession().call();
  }

  /**
   * Returns the username/email of the current session
   *
   * @return {@link String} not null
   */
  public String whoAmI() {
    return new MegaCmdWhoAmI().call();
  }

  /**
   * Uploads a single file/folder using the current remote working directory as destination for the
   * upload.
   *
   * @param localFilePath {@link String} with the path of the file/folder to upload
   * @return {@link MegaCmdPutSingle} to be configured and run
   * @see #uploadFile(String, String)
   * @see #uploadFiles(String, String...)
   */
  public MegaCmdPutSingle uploadFile(String localFilePath) {
    return new MegaCmdPutSingle(localFilePath);
  }

  /**
   * Uploads a single file/folder to a remote folder.
   *
   * @param localFilePath {@link String} with the path of the file/folder to upload
   * @param remotePath {@link String} of the remotePath where upload the content of {@code
   * localFilePath}
   * @return {@link MegaCmdPutSingle} to be configured and run
   * @see #uploadFile(String)
   * @see #uploadFiles(String, String...)
   */
  public MegaCmdPutSingle uploadFile(String localFilePath, String remotePath) {
    return new MegaCmdPutSingle(localFilePath, remotePath);
  }

  /**
   * Uploads multiple files/folders to a remote folder
   *
   * @param remotePath {@link String} with the remote path where to upload the files/folders
   * @param localFilesNames {@link String[]} with local files/folders to be uploaded
   * @return {@link MegaCmdPutMultiple} to be configured and run
   * @see #uploadFile(String, String)
   * @see #uploadFile(String)
   */
  public MegaCmdPutMultiple uploadFiles(
      String remotePath, String... localFilesNames
  ) {
    return new MegaCmdPutMultiple(remotePath, localFilesNames);
  }

  /**
   * Creates a directory or a directories hierarchy
   *
   * @param remotePath {@link String} with the path of the directory to create in MEGA
   * @return {@link MegaCmdMakeDirectory} to be configured and run.
   */
  public MegaCmdMakeDirectory makeDirectory(String remotePath) {
    return new MegaCmdMakeDirectory(remotePath);
  }

  /**
   * Copies a remote file/folder into a another remote location. to be configured and run. If the
   * {@code remoteTarget} exists and is a folder, the source will be copied there If the location
   * doesn't exits, the file/folder will be renamed to {@code remoteTarget}.
   *
   * @param remoteSourcePath {@link String} with remote path of the source file/folder
   * @param remoteTarget {@link String} with remote path where the file/folder will be copied
   * @return {@link MegaCmdCopy} to be configured and run.
   */
  public MegaCmdCopy copy(String remoteSourcePath, String remoteTarget) {
    return new MegaCmdCopy(remoteSourcePath, remoteTarget);
  }

  /**
   * Moves a file/folder into a new location (all remotes)
   *
   * @param remoteSourcePath {@link String} with remote path of the source file/folder
   * @param remoteTarget with remote path where the file/folder will be moved
   * @return {@link MegaCmdMove} to be configured and run.
   */
  public MegaCmdMove move(String remoteSourcePath, String remoteTarget) {
    return new MegaCmdMove(remoteSourcePath, remoteTarget);
  }

  /**
   * Lists files in a remote path. The param {@code remotepath} can be a pattern (it accepts
   * wildcards: ? and *. e.g.: f*00?.txt). Also, constructions like /PATTERN1/PATTERN2/PATTERN3 are
   * allowed
   *
   * @param remotePath {@link String} with the remote path to be listed
   * @return {@link MegaCmdList} to be configured and called
   */
  public MegaCmdList ls(String remotePath) {
    return new MegaCmdList(remotePath);
  }

  /**
   * Downloads a remote file/folder or a public link to the current folder. For folders, the entire
   * contents (and the root folder itself) will be by default downloaded.
   *
   * @param remotePath {@link String} with the remote path of the file/folder to be downloaded
   * @return {@link MegaCmdGet} to be configured and run.
   * @see #get(String, String)
   */
  public MegaCmdGet get(String remotePath) {
    return new MegaCmdGet(remotePath);
  }

  /**
   * Downloads a remote file/folder or a public link to {@code localPath}. For folders, the entire
   * contents (and the root folder itself) will be by default downloaded.
   *
   * @param remotePath {@link String} with the remote path of the file/folder to be downloaded
   * @param localPath {@link String} with the local path where to put the downloaded file/folder
   * @return {@link MegaCmdGet} to be configured and run.
   * @see #get(String)
   */
  public MegaCmdGet get(String remotePath, String localPath) {
    return new MegaCmdGet(remotePath, localPath);
  }

  /**
   * Removes a file in the specified (MEGA) remote path. Does not includes directories
   *
   * @param remotePath {@link String} with the remote path of the file to remove
   * @return {@link MegaCmdRemove} not null
   * @see #removeDirectory(String)
   */
  public MegaCmdRemove remove(String remotePath) {
    return new MegaCmdRemove(remotePath);
  }

  /**
   * This allows to delete directories in the specified (MEGA) remote path.
   *
   * @param remotePath {@link String} with the remote path of the directory to remove
   * @return {@link MegaCmdRemove} not null
   */
  public MegaCmdRemove removeDirectory(String remotePath) {
    return new MegaCmdRemove(remotePath).deleteRecursively();
  }

  /**
   * Count the amount of elements in a remote path. Includes folders and files
   *
   * @param remotePath {@link String} with the remote path
   * @return {@link Long} with the amount of elements
   * @see #count(String, Predicate)
   */
  public long count(String remotePath) {
    return ls(remotePath).count();
  }

  /**
   * Count the amount of elements in a remote path, filtering by a Predicate.
   *
   * @param remotePath {@link String} with the remote path
   * @param predicate {@link Predicate} filter to narrow elements to count
   * @return {@link Long} with the amount of elements
   * @see #count(String)
   */
  public long count(String remotePath, Predicate predicate) {
    return ls(remotePath).count(predicate);
  }

  public boolean exists(String remotePath) {
    return ls(remotePath).exists();
  }

  /**
   * Shares a resource hosted in Mega with a given user, giving him an specific
   * <code>MegaCmdShare.AccessLevel</code>.
   *
   * @param remotePath {@link String} with the remote path of the resource in MEGA to share
   * @param userMailToShareWith {@link String} with the username of the user to share the resource
   * with
   * @return {@link MegaCmdShare} not null
   * @see #export(String)
   */
  public MegaCmdShare share(String remotePath, String userMailToShareWith) {
    return new MegaCmdShare(remotePath, userMailToShareWith);
  }

  /**
   * Exports a remote resource located in MEGA to be used by a public yet secret url
   *
   * @param remotePath {@link String} with the remote path of the resource to be exported
   * @return {@link MegaCmdExport} not null
   * @see #share(String, String)
   */
  public MegaCmdExport export(String remotePath) {
    return new MegaCmdExport(remotePath);
  }

  /**
   * Enables HTTPS for transactions
   *
   * @return {@link Boolean} with true|false if it ran successfully or not
   */
  public Boolean enableHttps() {
    return new MegaCmdHttps(true).call();
  }

  /**
   * Disables HTTPS for transactions
   *
   * @return {@link Boolean} with false|true if it ran successfully or not
   */
  public Boolean disableHttps() {
    return new MegaCmdHttps(false).call();
  }

  /**
   * Requests if HTTPS is used for transactions
   *
   * @return {@link Boolean} with true|false if HTTPS is used for transactions or not
   */
  public Boolean isHttpsEnabled() {
    return new MegaCmdHttps().call();
  }
}
