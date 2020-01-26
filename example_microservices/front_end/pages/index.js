import Layout from '../components/MyLayout';
import Link from 'next/link';
import fetch from 'isomorphic-unfetch';


function getPosts() {
  return [
    { id: 'hello-nextjs', title: 'Hello Next.js' },
    { id: 'learn-nextjs', title: 'Learn Next.js is awesome' },
    { id: 'deploy-nextjs', title: 'Deploy apps with ZEIT' }
  ];
}

const PostLink = ({ post }) => (
  <li>
    <Link href="/p/[id]" as={`/p/${post.id}`}>
      <a>{post.title}</a>
    </Link>
    <style jsx>{`
    li {
      list-style: none;
      margin: 5px 0;
    }

    a {
      text-decoration: none;
      color: red;
      font-family: 'Arial';
    }

    a:hover {
      opacity: 0.6;
    }
  `}</style>
  </li>
);

const Index = props => (
    <Layout>
      <h1>Batman TV Shows</h1>
      <ul>
        {props.patients.map(patient => (
          <li key={patient.id}>
            <Link href="/p/[id]" as={`/p/${patient.id}`}>
              <a>{patient.firstName}</a>
            </Link>
          </li>
        ))}
      </ul>
      <style jsx>{`
        h1,
        a {
          font-family: 'Arial';
        }

        ul {
          padding: 0;
        }

        li {
          list-style: none;
          margin: 5px 0;
        }

        a {
          text-decoration: none;
          color: blue;
        }

        a:hover {
          opacity: 0.6;
        }
      `}</style>
    </Layout>
);

Index.getInitialProps = async function() {
  const res = await fetch('http://localhost:8080/allPatients');
  const data = await res.json();

  console.log(`Show data fetched. Count: ${data.length}`);

  return {
    patients: data
  };
};

export default Index;