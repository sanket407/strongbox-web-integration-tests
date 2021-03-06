import org.carlspring.strongbox.client.RestClient
import org.carlspring.strongbox.storage.repository.Repository
import org.carlspring.strongbox.storage.repository.RepositoryLayoutEnum
import org.carlspring.strongbox.storage.repository.RepositoryPolicyEnum

def client = RestClient.getTestInstanceLoggedInAsAdmin()

System.out.println()
System.out.println()
System.out.println("Host name: " + client.getHost())
System.out.println("Username:  " + client.getUsername())
System.out.println("Password:  " + client.getPassword())
System.out.println()
System.out.println()

try
{
    def configuration = client.getConfiguration()

    System.out.println()
    System.out.println("configuration = " + configuration)
    System.out.println()

    def storage = configuration.getStorage("storage0")

    System.out.println()
    System.out.println("storage = " + storage.toString())
    System.out.println()

    def repositoryId = "releases-with-redeployment"

    if (storage.getRepository(repositoryId) == null)
    {

        System.out.println()
        System.out.println()
        System.out.println("Creating a new repository with ID '" + repositoryId + "'...")
        System.out.println()
        System.out.println()

        // Create the test repository:
        def repository = new Repository(repositoryId)
        repository.setLayout(RepositoryLayoutEnum.MAVEN_2.layout)
        repository.setPolicy(RepositoryPolicyEnum.RELEASE.policy)
        repository.setAllowsRedeployment(true)
        repository.setStorage(storage)

        client.addRepository(repository)

        System.out.println()
        System.out.println()
        System.out.println("Repository '" + repositoryId + "' successfully created!")
        System.out.println()
        System.out.println()

        def s = configuration.getStorage("storage0")
        def r = client.getRepository("storage0", repositoryId)

        System.out.println()
        System.out.println()
        System.out.println("storage0: " + s.toString())
        System.out.println("releases-with-redeployment: " + r.toString())
        System.out.println()
        System.out.println()
    }

}
catch (Exception e)
{
    e.printStackTrace()
    return false
}
