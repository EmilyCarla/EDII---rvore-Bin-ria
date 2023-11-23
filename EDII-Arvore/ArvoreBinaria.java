import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ArvoreBinaria {
    NoArvore raiz = null;

    void Imp_Cres(NoArvore tree) {
        if (tree != null) {
            Imp_Cres(tree.esquerda);
            System.out.println(tree.info);
            Imp_Cres(tree.direita);
        }
    }

    NoArvore Search(NoArvore tree, int valor) {
        if (tree == null) {
            return null;
        } else if (tree.info > valor) {
            return Search(tree.esquerda, valor);
        } else if (tree.info < valor) {
            return Search(tree.direita, valor);
        } else {
            return tree;
        }
    }

    NoArvore InsereRaiz(NoArvore tree, int valor) {
        tree.info = valor;
        tree.direita = null;
        tree.esquerda = null;
        return tree;
    }

    NoArvore Insere(NoArvore tree, int valor) {
        if (tree == null) {
            tree = new NoArvore();
            tree.info = valor;
            tree.direita = null;
            tree.esquerda = null;
        } else if (valor < tree.info) {
            tree.esquerda = Insere(tree.esquerda, valor);
        } else {
            tree.direita = Insere(tree.direita, valor);
        }
        return tree;
    }

    NoArvore Retira(NoArvore tree, int valor) {
        if (tree == null) {
            System.out.println("Elemento não encontrado..: " + valor);
            return null;
        } else if (tree.info > valor) {
            tree.esquerda = Retira(tree.esquerda, valor);
        } else if (tree.info < valor) {
            tree.direita = Retira(tree.direita, valor);
        } else {
            if ((tree.esquerda == null) && (tree.direita == null)) {
                tree = null;
            } else if (tree.esquerda == null) {
                tree = tree.direita;
            } else if (tree.direita == null) {
                tree = tree.esquerda;
            } else {
                NoArvore novo = tree.esquerda;
                while (novo.direita != null) {
                    novo = novo.direita;
                }
                tree.info = novo.info;
                novo.info = valor;
                tree.esquerda = Retira(tree.esquerda, valor);
            }
        }
        return tree;
    }

    
    List<Integer> transformarArvoreEmLista(NoArvore arvore) {
        List<Integer> lista = new ArrayList<>();
        if (arvore != null) {
            lista.addAll(transformarArvoreEmLista(arvore.esquerda));
            lista.add(arvore.info);
            lista.addAll(transformarArvoreEmLista(arvore.direita));
        }
        return lista;
    }

    void realizarRotacoesParaEquilibrar(List<Integer> lista) {
        while (lista.size() > 1) {
            int no1 = lista.remove(0);
            int no2 = lista.remove(0);
            lista.add(no2);
            lista.add(no1);
        }
    }

    NoArvore reconstruirArvore(List<Integer> lista) {
        if (lista.isEmpty()) {
            return null;
        } else {
            NoArvore raiz = new NoArvore();
            raiz.info = lista.remove(0);
            ArvoreBinaria arvore = new ArvoreBinaria();
            arvore.raiz = raiz;
            while (!lista.isEmpty()) {
                NoArvore no = new NoArvore();
                no.info = lista.remove(0);
                arvore.Insere(raiz, no.info);
            }
            return arvore.raiz;
        }
    }

    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            arvore.raiz = arvore.Insere(arvore.raiz, random.nextInt(101));
        }

        
        System.out.println("Árvore antes do DSW:");
        arvore.Imp_Cres(arvore.raiz);

        List<Integer> lista = arvore.transformarArvoreEmLista(arvore.raiz);
        arvore.realizarRotacoesParaEquilibrar(lista);

        arvore.raiz = arvore.reconstruirArvore(lista);

        System.out.println("\nÁrvore após o DSW:");
        arvore.Imp_Cres(arvore.raiz);
    }
}

