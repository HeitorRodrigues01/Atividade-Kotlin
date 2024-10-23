data class Produto(
    val codigo: Int,
    var nome: String,
    var valorUnitario: Double,
    var tipoUnidade: String
)

val produtos = mutableListOf<Produto>()
val itensDaCompra = mutableListOf<Pair<Produto, Int>>()

fun adicionarProduto() {
    println("Informe o código do produto:")
    val codigo = readLine()!!.toInt()
    println("Informe o nome do produto:")
    val nome = readLine()!!
    println("Informe o valor unitário:")
    val valorString = readLine()!!
    val valorUnitario = valorString.replace(',', '.').toDouble()
    println("Informe o tipo de unidade (ex: kg, unidade, etc.):")
    val tipoUnidade = readLine()!!

    val produto = Produto(codigo, nome, valorUnitario, tipoUnidade)
    produtos.add(produto)

    println("Produto adicionado com sucesso!")
}

fun alterarProduto() {
    println("Informe o código do produto a ser alterado:")
    val codigo = readLine()!!.toInt()
    val produto = produtos.find { it.codigo == codigo }

    if (produto != null) {
        println("Novo nome do produto:")
        produto.nome = readLine()!!
        println("Novo valor unitário:")
        val valorString = readLine()!!
        produto.valorUnitario = valorString.replace(',', '.').toDouble()
        println("Novo tipo de unidade:")
        produto.tipoUnidade = readLine()!!

        println("Produto alterado com sucesso!")
    } else {
        println("Produto não encontrado!")
    }
}

fun excluirProduto() {
    println("Informe o código do produto a ser excluído:")
    val codigo = readLine()!!.toInt()
    val produto = produtos.find { it.codigo == codigo }

    if (produto != null) {
        produtos.remove(produto)
        println("Produto excluído com sucesso!")
    } else {
        println("Produto não encontrado!")
    }
}

fun realizarVenda() {
    while (true) {
        println("Informe o código do produto (ou digite 0 para finalizar a adição de itens):")
        val codigo = readLine()!!.toInt()
        if (codigo == 0) break

        val produto = produtos.find { it.codigo == codigo }
        if (produto != null) {
            println("Informe a quantidade:")
            val quantidadeString = readLine()!!

            val quantidade = try {
                quantidadeString.toInt()
            } catch (e: NumberFormatException) {
                println("Erro: A quantidade deve ser um número inteiro.")
                continue
            }

            itensDaCompra.add(Pair(produto, quantidade))
            println("Item adicionado à compra.")
        } else {
            println("Produto não encontrado.")
        }
    }
}

fun definirFormaDePagamento(valorTotal: Double) {
    println("Escolha a forma de pagamento: 1 - Pix, 2 - Cartão, 3 - Dinheiro")
    when (readLine()!!.toInt()) {
        1 -> println("Código PIX: abcdef123456")
        2 -> {
            println("Escolha o tipo de cartão: 1 - Crédito, 2 - Débito")
            val tipoCartao = readLine()!!.toInt()
            println("Informe os dados do cartão:")
            val numeroCartao = readLine()!!
            println("Nome do titular:")
            val nomeTitular = readLine()!!
            println("Validade (MM/AA):")
            val validade = readLine()!!
            println("CVV:")
            val cvv = readLine()!!
            println("Pagamento realizado com cartão $numeroCartao")
        }
        3 -> {
            println("Informe o valor pago:")
            val valorPagoString = readLine()!!
            val valorPago = valorPagoString.replace(',', '.').toDouble()
            val troco = valorPago - valorTotal
            if (troco < 0) {
                println("Valor pago insuficiente!")
            } else {
                println("Troco: R$ ${String.format("%.2f", troco)}")
            }
        }
        else -> println("Opção inválida.")
    }
}

fun finalizarCompra() {
    if (itensDaCompra.isEmpty()) {
        println("Nenhum item adicionado à compra.")
        return
    }

    var total = 0.0

    println("Itens da compra:")
    for ((produto, quantidade) in itensDaCompra) {
        val subtotal = produto.valorUnitario * quantidade
        total += subtotal
        println("${produto.nome} - $quantidade x R$ ${String.format("%.2f", produto.valorUnitario)} = R$ ${String.format("%.2f", subtotal)}")
    }

    println("Total: R$ ${String.format("%.2f", total)}")
    definirFormaDePagamento(total)

    itensDaCompra.clear()
}

fun main() {
    while (true) {
        println("1 - Cadastrar produto")
        println("2 - Alterar produto")
        println("3 - Excluir produto")
        println("4 - Realizar venda")
        println("5 - Finalizar compra")
        println("0 - Sair")

        when (readLine()!!.toInt()) {
            1 -> adicionarProduto()
            2 -> alterarProduto()
            3 -> excluirProduto()
            4 -> realizarVenda()
            5 -> finalizarCompra()
            0 -> break
            else -> println("Opção inválida")
        }
    }
}
